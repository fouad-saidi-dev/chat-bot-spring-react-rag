package com.fouadev.chatbotspringreactrag.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/*
 Created by : Fouad SAIDI on 14/07/2024
 @author : Fouad SAIDI
 @date : 14/07/2024
 @project : chat-bot-spring-react-rag
*/
@Component
public class RagDataLoader {
    private static final Logger logger = LoggerFactory.getLogger(RagDataLoader.class);

    @Value("classpath:/pdf/cv.pdf")
    private Resource resource;
    @Value("store-data-v1.json")
    private String storeFile;
    private JdbcClient jdbcClient;
    private VectorStore vectorStore;

    public RagDataLoader(JdbcClient jdbcClient, VectorStore vectorStore) {
        this.jdbcClient = jdbcClient;
        this.vectorStore = vectorStore;
    }

    //@Bean
    //@ConditionalOnMissingBean(SimpleVectorStore.class)
    public SimpleVectorStore loadData(EmbeddingModel embeddingModel) {

            SimpleVectorStore vectorStore = new SimpleVectorStore(embeddingModel);
            String fileStore = Path.of("src", "main", "resources", "store")
                    .toAbsolutePath() + "/" + storeFile;

            File file = new File(fileStore);

            if (!file.exists()) {
                logger.info("File not found, creating new vector store...");
                PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
                List<Document> documents = reader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> chunks = textSplitter.split(documents);
                logger.info("Number of document chunks: " + chunks.size());
                vectorStore.accept(chunks);
                vectorStore.save(file);
            } else {
                logger.info("Loading existing vector store...");
                vectorStore.load(file);
            }
            return vectorStore;

    }
    @PostConstruct
    public void initStore(){
        Integer count = jdbcClient.sql("select count(*) from vector_store").query(Integer.class).single();
        if(count == 0){
            PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
            List<Document> documents = reader.get();
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> chunks = textSplitter.split(documents);
            logger.info("Number of document chunks: " + chunks.size());
            vectorStore.add( chunks);
        }

    }

}