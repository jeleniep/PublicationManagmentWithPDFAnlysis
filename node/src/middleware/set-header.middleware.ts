import { Request, Response, NextFunction } from 'express';

const setHeaderMiddleware = (key: string, value: string) => (
    (req: Request, res: Response, next: NextFunction) => {
        res.setHeader(key, value);
        next();
    }
);

export default setHeaderMiddleware;
