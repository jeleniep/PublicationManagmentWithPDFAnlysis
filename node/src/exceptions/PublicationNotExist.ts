import HttpException from "./HttpException";

class PublicationNotExist extends HttpException {
    constructor(id: string) {
        super(
            403,
            'publication-not-exist',
            `Publication with id ${id} not exist.`
        );
    }
}

export default PublicationNotExist;
