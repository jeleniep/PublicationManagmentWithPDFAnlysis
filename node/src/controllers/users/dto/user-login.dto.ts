import { IsString, ArrayMinSize, IsObject, ValidateNested, IsArray, IsOptional, IsEmail } from 'class-validator';
import { Type } from 'class-transformer';

class UserLoginDto {
    
    @IsEmail()
    public readonly email: string;

    @IsString()
    public readonly password: string;

}

export default UserLoginDto;
