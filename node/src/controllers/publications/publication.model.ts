import mongoose, { Schema, Document } from 'mongoose';



export interface IPublication {
    name: String
}

export const PublicationSchema = new Schema({
    name: { type: String, required: false }
}, {
    timestamps: true
});



export type PublicationType = IPublication & Document


const Publication = mongoose.model<PublicationType>('Publication', PublicationSchema);
export default Publication;
