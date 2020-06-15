import HttpException from "./HttpException";

class RecordNotFoundException extends HttpException {
    constructor() {
        super(
            404,
            'not-found',
            `Record does not exist`
        );
    }
}

export default RecordNotFoundException;