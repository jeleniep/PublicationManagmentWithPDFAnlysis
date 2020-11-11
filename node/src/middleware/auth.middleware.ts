import { Request, Response, NextFunction } from 'express';
import axios from 'axios';
import { parseUrl } from '../helpers/whitelist.helper';
import { USER, ADMIN } from '../constants';

import {
    UserUnauthorizedException,
    NotEnoughPermissionsException,
    UserAccountNotExist,
    TokenNotFoundException,
    InvalidTokenException,
} from '../exceptions';
import Comment from '../controllers/comments/comment.model';
import { RedisService } from '../services';
import { sessionTime } from '../config';
import { Model, Document } from 'mongoose';
import { IOwnership } from '../interfaces';
import User, { UserType } from '../controllers/users/user.model';

const redisClient = RedisService.getClient();

const checkAuth = async (req: Request, next: NextFunction, profileTypes: string[]) => {
    let token: string;
    if (req.header('Authorization')) {
        token = req.header('Authorization').replace('Bearer', '').trim();
    } else {
        return next(new TokenNotFoundException());
    }

    if (token) {
        const session = await redisClient.hgetallAsync(token);
        console.log(session)
        if (session) {
            const expires = parseInt(session.expires);
            if (expires < (new Date()).getTime()) {
                return next(new InvalidTokenException());
            }
            if (profileTypes.indexOf(session.profile) >= 0) {
                await redisClient.hmsetAsync(token, "expires", String((new Date()).getTime() + sessionTime));
                (<any>req).user = {
                    username: session.username,
                    email: session.email,
                    _id: session._id,
                    expires: session.expires,
                    profile: session.profile,
                    token
                }
                return true;
            }
        };
    }
    return false;
}

export const authMiddlewareUser = async (req: Request, res: Response, next: NextFunction) => {
    const authStatus = await checkAuth(req, next, [USER, ADMIN]);
    if (authStatus) {
        return next();
    }
    return next(new UserUnauthorizedException());
}

export const authMiddlewareOwner = (DbModel: Model<IOwnership & Document>) => {
    return async (req: Request, res: Response, next: NextFunction) => {
        const authStatus = await checkAuth(req, next, [USER, ADMIN]);
        if (authStatus) {
            const { id } = req.params;
            const { _id, email } = (<any>req).user;
            let user: UserType;
            try {
                user = await User.findById(_id);
                if (!user) {
                    return next(new UserAccountNotExist(email));
                }
            } catch (error) {
                return next(new UserAccountNotExist(email));
            }

            let dbObject: IOwnership & Document;

            try {
                dbObject = await DbModel.findOne({ _id: id, owners: user });
                if (!dbObject) {
                    return next(new NotEnoughPermissionsException());
                }
            } catch (error) {
                return next(new NotEnoughPermissionsException());
            }
            return next();
        }
        return next(new UserUnauthorizedException());
    }
}

export const authMiddlewareAdmin = async (req: Request, res: Response, next: NextFunction) => {
    const authStatus = await checkAuth(req, next, [ADMIN]);
    if (authStatus) {
        return next();
    }
    return next(new UserUnauthorizedException());
}

export const authMiddlewareAdminOrMe = async (req: Request, res: Response, next: NextFunction) => {

    if (req.params.id === 'me') {
        const authStatus = await checkAuth(req, next, [USER, ADMIN]);
        if (authStatus) {
            return next();
        }
    } else {
        const authStatus = await checkAuth(req, next, [ADMIN]);
        if (authStatus) {
            return next();
        }
    }
    return next(new NotEnoughPermissionsException());
}