let mqtt = require('mqtt')

const OPTS = {
  connectionTimeout: 4000,
  clientId: 'JsSample',
  keepalive: 60,
  clean: true
}

const BASE_URL = '47.102.121.60'
const WENSOCKET_URL = `ws://${BASE_URL}:8083/mqtt`
//tcp node
const TCP_URL = `mqtt://${BASE_URL}:1883`
const TCP_TLS_URL = `mqtt://${BASE_URL}:8883`

const client = mqtt.connect(TCP_URL, OPTS)

/**
 * subscribe
 */
const TOPIC = "demo/topics"
client.subscribe(
  [TOPIC],
  {
    qos: 1
  },
  err => {
    console.log('subscribe success')
  }
)
client.on('message', (topic, message) => {
  console.log(`[subscribe]topic: ${topic}, msg: ${message.toString()}`)
})

/**
 * publish
 */
client.publish('demo/topics', 'message from MqttPublish - JavaScript', err => {
  console.log(err || '[publish] success')
})

client.on('connect', () => {
  console.log('connected')
})

client.on('reconnect', error => {
  connect('reconnecting: ', error)
})

client.on('error', error => {
  console.log('error connect: ', error)
})
