import { IsString, ArrayMinSize, IsObject, ValidateNested, IsArray, IsOptional, IsEmail } from 'class-validator';
import { Type } from 'class-transformer';

class UserRegisterDto {
    
    @IsEmail()
    public readonly email: string;

    @IsString()
    public readonly username: string;

    @IsString()
    public readonly password: string;

}

export default UserRegisterDto;
