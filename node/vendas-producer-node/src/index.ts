import express from 'express';
import redis from 'redis';
import { AddressInfo } from "net";

const redisClient = redis.createClient();

const STREAMS_KEY = "eventos-produtos";
const port = process.env.PORT || 8080;

const app = express();

app.post(["/node/enviar-produtos"], (_: express.Request, res: express.Response): void => {    
    redisClient.xadd(
        STREAMS_KEY,
        '*', 
        'produtor_vendas_node', '{"id":3,"descricao":"TV 32","valor":"R$ 3,245.00","caracteristica":{"ano":2020,"marca":"Sony"}', 
        function (err, id) {
            if (err) {
                return console.error(err);
            }
            return console.log(id);
        }
    );
    res.send("Mensagem enviada....")
});

const server = app.listen(port, (): void => {
    console.log(`Ready on http://localhost:${(server.address() as AddressInfo).port}`);
});
