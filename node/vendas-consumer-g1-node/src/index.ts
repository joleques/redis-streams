import async from 'async';
import redis from 'redis';

const redisClient = redis.createClient({
    host: "redis",
    port: 6379
});

const STREAMS_KEY = "eventos-produtos";
const APPLICATION_ID = "iot_application:node_1";
const CONSUMER_ID = "consumer:1"


// create the group
redisClient.xgroup("CREATE", STREAMS_KEY, APPLICATION_ID, '$', function(err) {
    if (err) {
        if (err.code == 'BUSYGROUP' ) {
            console.log(`Group ${APPLICATION_ID} already exists`);
        } else {
            console.log(err);
            process.exit();    
        }
    }
});

async.forever(
    function(next) {
        redisClient.xreadgroup('GROUP', APPLICATION_ID, CONSUMER_ID, 'BLOCK', 500, 'STREAMS',  STREAMS_KEY , '>', function (err, stream) {
            if (err) {
                console.error(err);
                next(err);
            }

            if (stream) {
                const messages = stream[0][1]; 
                // print all messages
                messages.forEach(function(message){
                    // convert the message into a JSON Object
                    const id = message[0];
                    const values = message[1];
                    const msgObject = { id : id};
                    for (let i = 0 ; i < values.length ; i=i+2) {
                        msgObject[values[i]] = values[i+1];
                    }                    
                    console.log( "Message: "+ JSON.stringify(msgObject));
                });
                
            } else {
                // No message in the consumer buffer
                console.log("No new message...");
            }

            next();
        });
    },
    function(err) {
        console.log(" ERROR " + err);
        process.exit()
    }
);