import HttpException from "./HttpException";

class PublicationNotExist extends HttpException {
    constructor(id: string) {
        super(
            404,
            'publication-not-exist',
            `Publication with id ${id} not exist.`
        );
    }
}

export default PublicationNotExist;
