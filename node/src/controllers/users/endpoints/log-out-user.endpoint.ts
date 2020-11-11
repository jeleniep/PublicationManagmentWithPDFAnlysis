import { Request, Response, NextFunction } from 'express';
import { RedisService } from '../../../services';
const logOutUser = async (req: Request, res: Response, next: NextFunction) => {
    const { token } = (<any>req).user
    const redisClient = RedisService.getClient();
    await redisClient.delAsync(token);
    res.json({
        status: 'ok'
    })
}

export default logOutUser