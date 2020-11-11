import redis from "redis";
import { promisify } from "util";

interface RedisServiceClient {
    redisClient: redis.RedisClient,
    hgetAsync: any,
    hmsetAsync: any,
    hgetallAsync: any,
    hdelAsync: any,
    delAsync: any,
    getKeysAsync: any
}

class RedisService {
    private static redisClient: redis.RedisClient;
    private static hgetAsync: any;
    private static hmsetAsync: any;
    private static hgetallAsync: any;
    private static hdelAsync: any;
    private static delAsync: any;
    private static getKeysAsync: any;

    private constructor() { }

    public static getClient(): RedisServiceClient {
        if (!RedisService.redisClient) {
            RedisService.redisClient = redis.createClient({ host: 'redis' });
            RedisService.hgetAsync = promisify(RedisService.redisClient.hmget).bind(RedisService.redisClient);
            RedisService.hmsetAsync = promisify(RedisService.redisClient.hmset).bind(RedisService.redisClient);
            RedisService.hgetallAsync = promisify(RedisService.redisClient.hgetall).bind(RedisService.redisClient);
            RedisService.hdelAsync = promisify(RedisService.redisClient.hdel).bind(RedisService.redisClient);
            RedisService.delAsync = promisify(RedisService.redisClient.del).bind(RedisService.redisClient);
            RedisService.getKeysAsync = promisify(RedisService.redisClient.keys).bind(RedisService.redisClient);
        }
        return {
            redisClient: RedisService.redisClient,
            hgetAsync: RedisService.hgetAsync,
            hmsetAsync: RedisService.hmsetAsync,
            hgetallAsync: RedisService.hgetallAsync,
            hdelAsync: RedisService.hdelAsync,
            delAsync: RedisService.delAsync,
            getKeysAsync: RedisService.getKeysAsync
        };
    }
}


export default RedisService