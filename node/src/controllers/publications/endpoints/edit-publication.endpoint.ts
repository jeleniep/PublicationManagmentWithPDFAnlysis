import { Request, Response, NextFunction } from 'express';
import Publication from "../publication.model";

const editPublication = async (req: Request, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const { name } = req.body;
    const publication = await Publication.findById(id);
    publication.name = name || publication.name;
    await publication.save();
    res.json(publication);
}

export default editPublication;
