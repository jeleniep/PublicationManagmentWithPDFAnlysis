import _ from 'lodash'
const extractDoi = (pdfData: any) => {
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

export default extractDoi;