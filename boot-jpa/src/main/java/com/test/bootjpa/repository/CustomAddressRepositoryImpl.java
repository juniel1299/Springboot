package com.test.bootjpa.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.bootjpa.dto.AddressDTO;
import com.test.bootjpa.entity.Address;
import com.test.bootjpa.entity.Info;
import com.test.bootjpa.entity.QAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;
import static com.test.bootjpa.entity.QAddress.address1;
import static com.test.bootjpa.entity.QInfo.info;
import static com.test.bootjpa.entity.QMemo.memo1;

@Repository
@RequiredArgsConstructor
public class CustomAddressRepositoryImpl implements CustomAddressRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Address> findAll() {

        /*

            - selectFrom : 해당 엔티티에서 모든 컬럼 조회
                - select * from 엔티티

            - fetch() : 리스트를 조회, 결과가 없으면 빈 리스트 반환
            - fetchOne() : 단일 조회, 결과가 없으면 null 반환, 결과가 다중값이면 Exception 발생
            - fetchFirst() : 단일 조회, 결과가 다중값이어도 처음것을 반환
            - fetchResults() : 페이징 정보 포함
            - fetchCount() : count 쿼리로 변경 후 count 반환

        */


        return jpaQueryFactory
                .selectFrom(address1)
                  .fetch();

    }

    public Address findAddressByName(String name){

        return jpaQueryFactory
                .selectFrom(address1)
                .where(address1.name.eq(name))
                .fetchFirst()
                ;
    }

    @Override
    public List<String> findName() {

        // selectFrom() = select * from 임
        // select() = select 컬럼 지정

        // select(QType.Column1, QType.Column2 ..)
        //from(QType) : 조회 대상 엔티티

        return jpaQueryFactory
                .select(address1.name)
                .from(address1)
                .fetch();
    }

    @Override
    public List<Tuple> findNameAndAge() {

        return jpaQueryFactory
                .select(address1.name,address1.age)
                .from(address1)
                .fetch();
    }

    @Override
    public List<AddressDTO> findNameAndAddress() {

        //Query DSL
        // - 결과 엔티티 > DTO 변환
        return jpaQueryFactory
                .select(Projections.constructor(AddressDTO.class,address1.name,address1.address))
                .from(address1)
                .fetch();
    }

    @Override
    public List<Address> findAddressByGender(String gender) {

        /*

            where()절
            - 동일 비교
                - address1.gender.eq("m");
                - address1.GENDER.ne("m");
                - address1.gender.isNull();
                - address1.gender.isNotNull();

            - 열거형
                - address1.age.in(3,5,7)
                - address1.age.notIn(3,5,7)


           - 범위 비교
                - address1.age.lt(3);
                - address1.age.gt(3);
                - address1.age.lte(3);
                - address1.age.goe(3);
                - address1.age.between(3,5);


           - 문자열
                - address1.address.startsWith("서울특별시 강동구");
                - address1.address.endWith("서울특별시 강동구");
                - address1.address.contains("빌딩");
                - address1.address.like("%아파트%");


           - and / or
                -


        */


        return jpaQueryFactory
                .selectFrom(address1)
                .where(address1.age.goe(3).and(address1.gender.eq("f")).and(address1.address.contains("강남구")))
                .fetch();
    }

    @Override
    public List<Address> findAddressOrderBy() {

        /*

            정렬

            - orderBy(QType.Column.정렬기준)

            정렬 기준
            - asc()
            - desc()
            - nullsLast()
            - nullsFirst()

        */

        return jpaQueryFactory
                .selectFrom(address1)
                //.orderBy(address1.age.desc(),address1.name.asc())
                .orderBy(address1.address.desc().nullsLast())
                .fetch();
    }

    @Override
    public List<Address> findAddressPagenation(int offset, int limit) {

        //페이징

        QueryResults<Address> result =  jpaQueryFactory
                .selectFrom(address1)
                .offset(offset)
                .limit(limit)
                .fetchResults();

        System.out.println("Total: " + result.getTotal());
        System.out.println("Offset : " + result.getOffset());
        System.out.println("Limit : " + result.getLimit());

        return result.getResults();


//        return jpaQueryFactory
//                .selectFrom(address1)
//                .offset(offset)
//                .limit(limit)
//                .fetch()
//                ;
    }

    @Override
    public int count() {

        return (int)jpaQueryFactory
                .selectFrom(address1)
                .fetchCount();
    }

    @Override
    public Tuple findAddressAggregation() {
        return jpaQueryFactory
                .select(address1.count(),address1.age.avg())
                .from(address1)
                .fetchOne()
                ;
    }

    @Override
    public List<Tuple> findAddressGroupByGender() {
        return jpaQueryFactory
                .select(address1.gender,address1.count(),address1.age.avg())
                .from(address1)
                .groupBy(address1.gender)
                .having(address1.age.avg().goe(4.5))
                .fetch();
    }

    @Override
    public List<Info> findAddressJoinInfo() {

        /*

            조인
            - join() : inner join
            - innerJoin() : inner join
            - leftJoin() : left outer join
            - rightJoin() : right outer join

        */
        return jpaQueryFactory
                .selectFrom(info) //자식테이블
                .join(info.address, address1) //join(연관관계, 부모테이블)
                .fetch();
    }

    @Override
    public List<Address> findAddressJoinMemo() {

        // - inner join
        // - left outer join

//        return jpaQueryFactory
//                .selectFrom(address1)
//                .join(address1.memo1,memo1)
//                .fetch();
        return jpaQueryFactory
                .selectFrom(address1)
                .join(address1.memo,memo1)
                .fetch()
                ;
    }

    @Override
    public List<Info> findAddressFullJoin() {

        return jpaQueryFactory
                .selectFrom(info)
                .join(info.address, address1)
                .leftJoin(address1.memo,memo1)
                .orderBy(address1.seq.asc())
                .fetch();
    }

    @Override
    public List<Address> findAddressByMaxAge() {


        // select * from tblAddress where age = (select max(age) from tblAddress);

        QAddress subAddress = QAddress.address1;

        return jpaQueryFactory
                .selectFrom(address1)
                .where(address1.age.eq(
                        select(subAddress.age.max())
                        .from(subAddress)))
                .fetch();
    }

    @Override
    public List<Tuple> findAddressByAvgAge() {

        //select name, age, 평균나이, from tblAddress

        QAddress subAddress = new  QAddress(address1);


        return jpaQueryFactory
                .select(
                        address1.name,
                        address1.age,
                        JPAExpressions.select(subAddress.age.avg()).from(subAddress)
                )
                .from(address1)
                .fetch();
    }

    @Override
    public List<Address> findAddressByMultiParameter(String gender, Integer age) {

        BooleanBuilder builder = new BooleanBuilder();

        if(gender != null) {
            builder.and(address1.gender.eq(gender));
        }
        if(age != null) {
        builder.and(address1.age.eq(age));
}
//        return jpaQueryFactory
//                .selectFrom(address1)
//                .where(builder)
//                .fetch()
//                ;


        return jpaQueryFactory
                .selectFrom(address1)
                .where(genderEq(gender),ageEq(age))
                .fetch()
                ;
    }
private BooleanExpression genderEq(String gender){
        return gender != null ? address1.gender.eq(gender) : null;
}

    private BooleanExpression ageEq(Integer age){
        return age != null ? address1.age.eq(age) : null;
    }

}
