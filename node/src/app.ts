import express from 'express';
import helmet from 'helmet';
import compression from 'compression';
import bodyParser from 'body-parser';
import cors from 'cors';
import morgan from 'morgan';
import { createConnection } from "typeorm";


import Controller from './interfaces/controller.interface';
import {
    errorMiddleware,
} from './middleware';

import {
    APP_PORT
} from './config';
import "reflect-metadata";



class App {
    public app: express.Application;

    constructor(controllers: Controller[]) {
        this.app = express();

        this.initializeMiddlewares();
        this.initializeControllers(controllers);
        this.initializeErrorHandling();
    }


    public listen() {
        this.app.listen(APP_PORT, () => {
            console.log('Server is up and running:');
            console.log(`  PORT: ${APP_PORT}`);
        });
    }

    private initializeMiddlewares() {
        this.app.use(compression());
        this.app.use(helmet());
        this.app.use(cors());

        this.app.use(bodyParser.urlencoded({ extended: true }));
        this.app.use(bodyParser.json());

        this.app.use(morgan(':method :url :status :res[content-length] - :response-time ms'));



    }

    private initializeControllers(controllers: Controller[]) {
        controllers.forEach((controller) => {
            this.app.use('/v1', controller.router);
        });
    }

    private initializeErrorHandling() {
        this.app.use(errorMiddleware);
    }
}

export default App;
