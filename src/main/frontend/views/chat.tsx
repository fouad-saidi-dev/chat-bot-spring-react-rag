import {Button, TextField} from "@vaadin/react-components";
import {useState} from "react";
import {ChatService} from "Frontend/generated/endpoints";
import Markdown from "react-markdown";

export default function Chat() {
    const [question, setQuestion] = useState<string>('');
    const [response,setResponse ] = useState<string>('');

    function handleQuestionChange(e: any) {
        setQuestion(e.target.value);
    }

    function handleResponseChange(e: any) {
        setResponse(e.target.value);
    }

    async function handleSend() {
        ChatService.regChat(question)
            .then(res => {
                setResponse(res);
            })
            .catch(err => {
                console.log(err);
            })
    }



    return (
        <div className="p-m">
            <h1>Chat Bot</h1>
            <div>
                <TextField
                    label="Enter your message here..."
                    style={{width: '100%',height: '100px'}}
                    onChange={(e=>setQuestion(e.target.value))}
                />
                <Button theme={'primary'} onClick={handleSend}>Send</Button>
                <div>
                    <Markdown>{response}</Markdown>
                </div>
            </div>
        </div>
    );
}