import { Request, Response, NextFunction } from 'express';
import Publication from "../publication.model";

const getPublications = async (req: Request, res: Response, next: NextFunction) => {
    const publications = await Publication.find();
    res.json(publications);
}

export default getPublications;
