import { Request, Response, NextFunction } from 'express';
import HttpException from '../exceptions/HttpException';

const errorMiddleware = (err: HttpException, req: Request, res: Response, next: NextFunction) => {
    const status = err.status || 500;
    const code = err.code || 'unknown-error';
    const message = err.message || 'Unknown error happened on the server';
    console.log(`Error: status: ${status}, code: ${code}, message: ${message}`);
    console.log(`${err.stack}`);
    res.status(status).json({
        code,
        message
    });
}
export default errorMiddleware;
