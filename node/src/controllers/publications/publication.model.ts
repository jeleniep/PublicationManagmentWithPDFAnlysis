import mongoose, { Schema, Document } from 'mongoose';
import { UserType } from '../users/user.model';
import { IOwnership } from '../../interfaces';



export interface IPublication {
    name: string,
    authors: string[],
    file?: string,
    description: string,
    tags: string[],

}

export const PublicationSchema = new Schema({
    name: { type: String, required: true },
    authors: [{ type: String, required: false }],
    file: { type: String, required: false },
    owners: [{  type: mongoose.Types.ObjectId, ref: 'User', required: true }],
    description: { type: String, required: false },
    tags: [{ type: String, required: false }]
}, {
    timestamps: true
});



export type PublicationType = IPublication & Document & IOwnership


const Publication = mongoose.model<PublicationType>('Publication', PublicationSchema);
export default Publication;
