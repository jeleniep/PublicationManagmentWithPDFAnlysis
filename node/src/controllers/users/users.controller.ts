
import { Router, Request, Response, NextFunction } from 'express';
import _ from 'lodash';
import bcrypt from 'bcrypt';
import Controller from '../../interfaces/controller.interface';
import {
    wrapAsync,
    authMiddlewareUser,
    validationMiddleware,
    authMiddlewareAdmin,
    authMiddlewareAdminOrMe
} from '../../middleware';

import App from '../../app';
import {
    UserLoginDto,
    EditUserAccountDto,
    EditUserDto,
    UserRegisterDto,
} from './dto';
import {
    getUsers,
    getUser,
    editUser,
    editUserAccount,
    deleteUser,
    authUser,
    logOutUser,
    registerUser
} from './endpoints'
import User from './user.model';
import { ADMIN } from '../../constants';
import { SALT_ROUNDS, ADMIN_EMAIL, ADMIN_PASSWORD } from '../../config';

class UsersController implements Controller {
    public readonly path = '/users';
    public readonly router = Router();
    public app: App;

    constructor() {
        this.initializeRoutes();
        this.updateAdmin();
    }

    private initializeRoutes() {
        const router = Router();
        router
            .get('/:id', authMiddlewareUser, wrapAsync(getUser))
            .get('/', authMiddlewareUser, wrapAsync(getUsers))
            .put('/:id', authMiddlewareAdmin, validationMiddleware(EditUserDto), wrapAsync(editUser))
            .put('/', validationMiddleware(EditUserAccountDto), wrapAsync(editUserAccount))
            .delete('/:id', authMiddlewareAdminOrMe,  wrapAsync(deleteUser))
            .post('/auth', validationMiddleware(UserLoginDto), wrapAsync(authUser))
            .post('/logout', authMiddlewareUser, wrapAsync(logOutUser))
            .post('/register', validationMiddleware(UserRegisterDto), wrapAsync(registerUser))
        this.router.use(
            this.path,
            router
        );
    }

    private updateAdmin = async () => {
        await User.findOneAndUpdate(
            { username: ADMIN },
            { $set: { email: ADMIN_EMAIL, hash: await bcrypt.hash(ADMIN_PASSWORD, SALT_ROUNDS), profile: ADMIN } },
            { upsert: true, new: true }
        );
    }
}

export default UsersController;
