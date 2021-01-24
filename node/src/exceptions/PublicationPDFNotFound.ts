import HttpException from "./HttpException";

class PublicationPDFNotFound extends HttpException {
    constructor() {
        super(
            400,
            'Pdf-not-found',
            'You have to select PDF first.'
        );
    }
}

export default PublicationPDFNotFound;
