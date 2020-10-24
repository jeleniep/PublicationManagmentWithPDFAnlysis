import { Request, Response, NextFunction } from 'express';
import Publication from '../publication.model';


const addPublication = async (req: Request, res: Response, next: NextFunction) => {
    const { name } = req.body;
    console.log(name);
    const publication = new Publication();
    publication.name = name;
    await publication.save();
    res.json(publication);
}

export default addPublication;