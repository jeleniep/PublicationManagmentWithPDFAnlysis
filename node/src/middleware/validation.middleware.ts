import "reflect-metadata";
import { plainToClass } from 'class-transformer';
import { validate, ValidationError } from 'class-validator';
import * as express from 'express';
import HttpException from '../exceptions/HttpException';
import _ from "lodash";

function validationMiddleware<T>(type: any, skipMissingProperties = false): express.RequestHandler {
    return (req, res, next) => {
        validate(plainToClass(type, req.body), { skipMissingProperties })
            .then((errors: ValidationError[]) => {
                if (errors.length > 0) {
                    const message = parseError(errors)                   
                    next(new HttpException(400, 'validation-error', message));
                }
                else {
                    next();
                }
            });
    };
}

const parseError = (errors: ValidationError[]): string => {
    let message = "";
    for (const error of errors) {
        if (!_.isEmpty(error.children)) {
            message = message.length > 0 ? message.concat(', ') : message;
            message = message.concat(parseError(error.children));
        }
        if (error.constraints) {
            const localMessage = Object.values(error.constraints);
            if (localMessage) {
                message = message.length > 0 ? message.concat(', ') : message;
                message = message.concat(localMessage.join(', '))
            }
        }
    }
    return message;

}

export default validationMiddleware;

