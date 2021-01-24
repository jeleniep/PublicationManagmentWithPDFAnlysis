import pdfParser from 'pdf-parse';
import _ from 'lodash'
import {promises as fs} from 'fs'
import axios from 'axios'
import { DOI_DETAILS_API } from '../config'
import { IPublicationDetails } from '../interfaces';


export default class PdfParserHelper {

    private constructor() { }

    static getPdfData = async (path: string) => {
        const file = await fs.readFile(path);
        const pdfData = await pdfParser(file);
        const publication: IPublicationDetails = {}
        publication.name = pdfData.info.Title;
        publication.authors = pdfData.info.Author ? [pdfData.info.Author] : [];
        publication.doi = PdfParserHelper.extractDoi(pdfData);

        const detailsFromDoi = await PdfParserHelper.getDetailsFromDoi(publication.doi)
        if (detailsFromDoi) {
            publication.name = detailsFromDoi.name;
        }
        return publication;
    }

    static extractDoi = (pdfData: any) => {
        if (!pdfData) {
            return undefined;
        }
        let doi: string = undefined
        if (pdfData.metadata) {
            doi = _.get(pdfData, ['metadata', '_metadata', 'pdfx:doi']);
            console.log(pdfData)
            if (doi) {
                console.log("Doi Meta")
                return doi;
            }
        }
        if (pdfData.text) {
    
            const regex = /\S+(doi.org)\S+/g;
            const found = pdfData.text.match(regex);
            if (found) {
                const temp = found[0].split("/")
                if (temp.length >= 2) {
                    console.log("Doi TEXT")

                    doi = `${temp[temp.length - 2]}/${temp[temp.length - 1]}`;
                }
            }
            return doi;
        }
    
    
    
        return doi;
    };
    
    static getDetailsFromDoi = async (doi: string): Promise<IPublicationDetails> => {
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
            // console.log(response.data)
            details.name = response.data.message.title.join(' ');
            return details;
        }
        return undefined;
    
    }
}
