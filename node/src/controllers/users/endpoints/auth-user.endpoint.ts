import { Request, Response, NextFunction } from 'express';
import only from 'only';
import User from '../user.model';
import bcrypt from 'bcrypt';
import crypto from 'crypto';
import { IncorrectCredentialsException, IncorrectPasswordException, UserAccountNotExist } from '../../../exceptions';
import { USER } from '../../../constants';
import { RedisService } from '../../../services';
import { sessionTime } from '../../../config';

const authUser = async (req: Request, res: Response, next: NextFunction) => {
    const { email, password } = req.body;

    const redisClient = RedisService.getClient();
 
    const lowerCaseEmail = (<string>email).toLowerCase();
    const user = await User.findOne({ email: lowerCaseEmail });
    if (!user) {
        throw new UserAccountNotExist(lowerCaseEmail);
    }
    const authStatus = await bcrypt.compare(password, user.hash);
    if (authStatus) {
        let randomToken = crypto.randomBytes(32).toString('hex');
        let tokenCollison = await redisClient.hgetallAsync(randomToken);
        while (tokenCollison) {
            randomToken = crypto.randomBytes(32).toString('hex');
            tokenCollison = await redisClient.hgetallAsync(randomToken);
        }
        await redisClient.hmsetAsync(randomToken, "username", user.username);
        await redisClient.hmsetAsync(randomToken, "email", user.email);
        await redisClient.hmsetAsync(randomToken, "_id", String(user._id));
        await redisClient.hmsetAsync(randomToken, "expires", String((new Date()).getTime() + sessionTime));
        await redisClient.hmsetAsync(randomToken, "profile", String(user.profile));

        const reducedUserObject = only(user.toObject(), 'email username type _id profile ')
        res.setHeader('Account-username', user.username)
        return res.json({ ...reducedUserObject, authToken: randomToken });
    }
    throw new IncorrectPasswordException();
}

export default authUser