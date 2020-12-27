import _ from 'lodash'
import axios from 'axios'
import { DOI_DETAILS_API } from '../config'
import { IPublicationDetails } from '../interfaces';

export const extractDoi = (pdfData: any) => {
    if (!pdfData) {
        return undefined;
    }
    let doi: string = undefined
    if (pdfData.metadata) {
        doi = _.get(pdfData, ['metadata', '_metadata', 'pdfx:doi']);
        console.log(pdfData)
        if (doi) {
            console.log("1")
            return doi;
        }
    }
    if (pdfData.text) {

        const regex = /\S+(doi.org)\S+/g;
        const found = pdfData.text.match(regex);
        if (found) {
            const temp = found[0].split("/")
            if (temp.length >= 2) {
                doi = `${temp[temp.length - 2]}/${temp[temp.length - 1]}`;
            }
        }
        return doi;
    }



    return doi;
};

export const getDetailsFromDoi = async (doi: string): Promise<IPublicationDetails> => {
    const API = axios.create({
        baseURL: DOI_DETAILS_API
    });
    const details: IPublicationDetails = {};
    if (!doi) {
        return undefined;
    }
    const response = await API.get(doi)
    const responseData = response.data;
    if (responseData.status !== 'ok') {
        return undefined;
    }
    if (responseData.message) {
        console.log(response.data)
        details.name = response.data.message.title.join(' ');
        return details;
    }
    return undefined;

}