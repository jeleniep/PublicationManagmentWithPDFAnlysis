import { Request, Response, NextFunction } from 'express';
import Publication from "../publication.model";

const deletePublication = async (req: Request, res: Response, next: NextFunction) => {
    const {id} = req.params;
    const publication = await Publication.findByIdAndDelete(id);
    res.json(publication);
}

export default deletePublication;
