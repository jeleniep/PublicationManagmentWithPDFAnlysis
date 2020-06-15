
import { Router, Request, Response, NextFunction } from 'express';
import Controller from '../interfaces/controller.interface';
import { getDbConnection } from '../services'
import { wrapAsync } from '../middleware';
import { createQueryBuilder } from 'typeorm'
import { promises as fs } from 'fs';
import pdf from 'pdf-parse'

class PdfController implements Controller {
    public readonly path = '/pdfs';
    public readonly router = Router();
    constructor() {
        this.initializeRoutes();
    }

    private initializeRoutes() {
        const router = Router();
        router
            .get('/', wrapAsync(this.getPdfs))


        this.router.use(
            this.path,
            router
        );
    }


    private getPdfs = async (req: Request, res: Response, next: NextFunction) => {
        const file = await fs.readFile("opis.pdf");
        const pdfData = await pdf(file);
        res.json(pdfData);
    }





}

export default PdfController;
