import HttpException from "./HttpException";

class RecordFilenameRequiredException extends HttpException {
    constructor() {
        super(
            400,
            'filename-required',
            'Filename field is required for rename'
        );
    }
}

export default RecordFilenameRequiredException;
