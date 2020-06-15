// import {Entity, PrimaryGeneratedColumn, Column, BaseEntity, ManyToMany, JoinTable, ManyToOne, OneToOne} from "typeorm";
// import Reader from './reader.model';
// import Rent from './rent.model';

// @Entity()
// export default class Fee extends BaseEntity {

//     @PrimaryGeneratedColumn()
//     id: number;

//     @Column("decimal")
//     cost: number;

//     @Column("boolean")
//     paid: boolean;

//     // @OneToOne(type => Rent, rent => rent.fee)
//     @JoinTable()
//     rent: Rent;

//     // @ManyToOne(type => Reader, reader => reader.rents)
//     @JoinTable()
//     reader: Reader;
// }