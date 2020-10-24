import { Request, Response, NextFunction } from 'express';
import Publication from "../publication.model";

const getPublication = async (req: Request, res: Response, next: NextFunction) => {
    const {id} = req.params;
    const publication = await Publication.findById(id);
    res.json(publication);
}

export default getPublication;
