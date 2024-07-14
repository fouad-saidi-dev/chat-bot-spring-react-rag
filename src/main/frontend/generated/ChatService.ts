import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
async function regChat_1(question: string, init?: EndpointRequestInit_1): Promise<string> { return client_1.call("ChatService", "regChat", { question }, init); }
export { regChat_1 as regChat };
