package com.linecorp.kotlinjdsl.example.eclipselink

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.eclipselink.entity.author.Author
import com.linecorp.kotlinjdsl.example.eclipselink.entity.book.Book
import com.linecorp.kotlinjdsl.example.eclipselink.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.example.eclipselink.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.eclipselink.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.eclipselink.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.example.eclipselink.entity.publisher.Publisher
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.eclipselink.extension.createQuery
import jakarta.persistence.Persistence
import org.assertj.core.api.WithAssertions
import org.eclipse.persistence.jpa.JpaEntityManager
import org.junit.jupiter.api.Test

class SelectExample : WithAssertions {
    private val entityManagerFactory = Persistence.createEntityManagerFactory("example")

//    @BeforeEach
//    fun setUp() {
//        val entityManager = entityManagerFactory.createEntityManager()
//
//        entityManager.delegate
//
//        val transaction = entityManager.transaction
//        transaction.begin()
//
//
//        entityManager.createNativeQuery(
//            //language=sql
//            """
//                insert into author (author_id, name)
//                values (1, 'Author01'),
//                       (2, 'Author02'),
//                       (3, 'Author03'),
//                       (4, 'Author04')
//                ;
//            """.trimIndent(),
//        ).executeUpdate()
//        entityManager.createNativeQuery(
//            //language=sql
//            """
//                insert into publisher (publisher_id, name)
//                values (1, 'Publisher01'),
//                       (2, 'Publisher02'),
//                       (3, 'Publisher03'),
//                       (4, 'Publisher04')
//                ;
//            """.trimIndent(),
//        ).executeUpdate()
//        entityManager.createNativeQuery(
//            //language=sql
//            """
//                insert into book (isbn, title, image_url, publish_date, price, sale_price)
//                values ('01', 'Book01', 'BookImageUrl01', '2023-01-01T00:00:00.000000+09:00', 01, 01),
//                       ('02', 'Book02', 'BookImageUrl02', '2023-02-01T00:00:00.000000+09:00', 02, 02),
//                       ('03', 'Book03', 'BookImageUrl03', '2023-03-01T00:00:00.000000+09:00', 03, 03),
//                       ('04', 'Book04', 'BookImageUrl04', '2023-04-01T00:00:00.000000+09:00', 04, 04),
//                       ('05', 'Book05', 'BookImageUrl05', '2023-05-01T00:00:00.000000+09:00', 05, 05),
//                       ('06', 'Book06', 'BookImageUrl06', '2023-06-01T00:00:00.000000+09:00', 06, 06),
//                       ('07', 'Book07', 'BookImageUrl07', '2023-07-01T00:00:00.000000+09:00', 07, 07),
//                       ('08', 'Book08', 'BookImageUrl08', '2023-08-01T00:00:00.000000+09:00', 08, 08),
//                       ('09', 'Book09', 'BookImageUrl09', '2023-09-01T00:00:00.000000+09:00', 09, 09),
//                       ('10', 'Book10', 'BookImageUrl10', '2023-10-01T00:00:00.000000+09:00', 10, 10),
//                       ('11', 'Book11', 'BookImageUrl11', '2023-11-01T00:00:00.000000+09:00', 11, 10),
//                       ('12', 'Book12', 'BookImageUrl12', '2023-12-01T00:00:00.000000+09:00', 12, 10)
//                ;
//            """.trimIndent(),
//        ).executeUpdate()
//        entityManager.createNativeQuery(
//            //language=sql
//            """
//                insert into book_author (isbn, author_id)
//                values ('01', 1),
//                       ('01', 2),
//                       ('01', 3),
//                       ('02', 1),
//                       ('02', 2),
//                       ('03', 1),
//                       ('04', 1),
//                       ('05', 1),
//                       ('06', 1),
//                       ('07', 2),
//                       ('08', 2),
//                       ('09', 2),
//                       ('10', 3),
//                       ('11', 3),
//                       ('12', 3)
//                ;
//            """.trimIndent(),
//        ).executeUpdate()
//        entityManager.createNativeQuery(
//            //language=sql
//            """
//                insert into book_publisher (isbn, publisher_id)
//                values ('01', 1),
//                       ('02', 1),
//                       ('03', 1),
//                       ('04', 1),
//                       ('05', 1),
//                       ('06', 2),
//                       ('07', 2),
//                       ('08', 2),
//                       ('09', 2),
//                       ('10', 3),
//                       ('11', 3),
//                       ('12', 3)
//                ;
//            """.trimIndent(),
//        ).executeUpdate()
//        entityManager.createNativeQuery(
//            //language=sql
//            """
//                insert into department (department_id, name)
//                values (1, 'Department01'),
//                       (2, 'Department02'),
//                       (3, 'Department03'),
//                       (4, 'Department04')
//                ;
//            """.trimIndent(),
//        ).executeUpdate()
//        entityManager.createNativeQuery(
//            //language=sql
//            """
//                insert into employee (employee_id, employee_type, name, nickname, phone, zip_code, street_address_1, street_address_2, city, province, annual_salary, weekly_salary)
//                values (01, 'PART_TIME', 'Employee01', null,         'Phone01', 'ZipCode01', 'StreetAddress101', null,               'City01', 'Province01', null, 100),
//                       (02, 'PART_TIME', 'Employee02', null,         'Phone02', 'ZipCode02', 'StreetAddress102', null,               'City02', 'Province02', null, 200),
//                       (03, 'PART_TIME', 'Employee03', null,         'Phone03', 'ZipCode03', 'StreetAddress103', null,               'City03', 'Province03', null, 300),
//                       (04, 'PART_TIME', 'Employee04', null,         'Phone04', 'ZipCode04', 'StreetAddress104', null,               'City04', 'Province04', null, 400),
//                       (05, 'PART_TIME', 'Employee05', null,         'Phone05', 'ZipCode05', 'StreetAddress105', null,               'City05', 'Province05', null, 500),
//                       (06, 'PART_TIME', 'Employee06', null,         'Phone06', 'ZipCode06', 'StreetAddress106', null,               'City06', 'Province06', null, 600),
//                       (07, 'PART_TIME', 'Employee07', null,         'Phone07', 'ZipCode07', 'StreetAddress107', null,               'City07', 'Province07', null, 700),
//                       (08, 'PART_TIME', 'Employee08', 'Nickname08', 'Phone08', 'ZipCode08', 'StreetAddress108', 'StreetAddress208', 'City08', 'Province08', null, 800),
//                       (09, 'PART_TIME', 'Employee09', 'Nickname09', 'Phone09', 'ZipCode09', 'StreetAddress109', 'StreetAddress209', 'City09', 'Province09', null, 900),
//                       (10, 'PART_TIME', 'Employee10', 'Nickname10', 'Phone10', 'ZipCode10', 'StreetAddress110', 'StreetAddress210', 'City10', 'Province10', null, 1000),
//                       (11, 'PART_TIME', 'Employee11', 'Nickname11', 'Phone11', 'ZipCode11', 'StreetAddress111', 'StreetAddress211', 'City11', 'Province11', null, 1100),
//                       (12, 'PART_TIME', 'Employee12', 'Nickname12', 'Phone12', 'ZipCode12', 'StreetAddress112', 'StreetAddress212', 'City12', 'Province12', null, 1200),
//                       (13, 'PART_TIME', 'Employee13', 'Nickname13', 'Phone13', 'ZipCode13', 'StreetAddress113', 'StreetAddress213', 'City13', 'Province13', null, 1300),
//                       (14, 'PART_TIME', 'Employee14', 'Nickname14', 'Phone14', 'ZipCode14', 'StreetAddress114', 'StreetAddress214', 'City14', 'Province14', null, 1400),
//                       (15, 'PART_TIME', 'Employee15', 'Nickname15', 'Phone15', 'ZipCode15', 'StreetAddress115', 'StreetAddress215', 'City15', 'Province15', null, 1500),
//                       (16, 'FULL_TIME', 'Employee16', null,         'Phone16', 'ZipCode16', 'StreetAddress116', null,               'City16', 'Province16', 1600, null),
//                       (17, 'FULL_TIME', 'Employee17', null,         'Phone17', 'ZipCode17', 'StreetAddress117', null,               'City17', 'Province17', 1700, null),
//                       (18, 'FULL_TIME', 'Employee18', null,         'Phone18', 'ZipCode18', 'StreetAddress118', null,               'City18', 'Province18', 1800, null),
//                       (19, 'FULL_TIME', 'Employee19', null,         'Phone19', 'ZipCode19', 'StreetAddress119', null,               'City19', 'Province19', 1900, null),
//                       (20, 'FULL_TIME', 'Employee20', null,         'Phone20', 'ZipCode20', 'StreetAddress120', null,               'City20', 'Province20', 2000, null),
//                       (21, 'FULL_TIME', 'Employee21', null,         'Phone21', 'ZipCode21', 'StreetAddress121', null,               'City21', 'Province21', 2100, null),
//                       (22, 'FULL_TIME', 'Employee22', null,         'Phone22', 'ZipCode22', 'StreetAddress122', null,               'City22', 'Province22', 2200, null),
//                       (23, 'FULL_TIME', 'Employee23', null,         'Phone23', 'ZipCode23', 'StreetAddress123', null,               'City23', 'Province23', 2300, null),
//                       (24, 'FULL_TIME', 'Employee24', 'Nickname24', 'Phone24', 'ZipCode24', 'StreetAddress124', 'StreetAddress224', 'City24', 'Province24', 2400, null),
//                       (25, 'FULL_TIME', 'Employee25', 'Nickname25', 'Phone25', 'ZipCode25', 'StreetAddress125', 'StreetAddress225', 'City25', 'Province25', 2500, null),
//                       (26, 'FULL_TIME', 'Employee26', 'Nickname26', 'Phone26', 'ZipCode26', 'StreetAddress126', 'StreetAddress226', 'City26', 'Province26', 2600, null),
//                       (27, 'FULL_TIME', 'Employee27', 'Nickname27', 'Phone27', 'ZipCode27', 'StreetAddress127', 'StreetAddress227', 'City27', 'Province27', 2700, null),
//                       (28, 'FULL_TIME', 'Employee28', 'Nickname28', 'Phone28', 'ZipCode28', 'StreetAddress128', 'StreetAddress228', 'City28', 'Province28', 2800, null),
//                       (29, 'FULL_TIME', 'Employee29', 'Nickname29', 'Phone29', 'ZipCode29', 'StreetAddress129', 'StreetAddress229', 'City29', 'Province29', 2900, null),
//                       (30, 'FULL_TIME', 'Employee30', 'Nickname30', 'Phone30', 'ZipCode30', 'StreetAddress130', 'StreetAddress230', 'City30', 'Province30', 3000, null)
//                ;
//            """.trimIndent(),
//        ).executeUpdate()
//        entityManager.createNativeQuery(
//            //language=sql
//            """
//                insert into employee_department (employee_id, department_id)
//                values (01, 01),
//                       (01, 02),
//                       (01, 03),
//                       (02, 02),
//                       (03, 02),
//                       (04, 02),
//                       (05, 02),
//                       (06, 02),
//                       (07, 02),
//                       (07, 03),
//                       (08, 02),
//                       (08, 03),
//                       (09, 03),
//                       (10, 03),
//                       (11, 03),
//                       (12, 03),
//                       (13, 03),
//                       (14, 03),
//                       (15, 01),
//                       (16, 01),
//                       (17, 01),
//                       (18, 02),
//                       (19, 01),
//                       (19, 02),
//                       (20, 02),
//                       (21, 02),
//                       (21, 03),
//                       (22, 02),
//                       (23, 02),
//                       (23, 03),
//                       (24, 03),
//                       (25, 03),
//                       (26, 03),
//                       (27, 01),
//                       (27, 02),
//                       (27, 03),
//                       (28, 03),
//                       (29, 03),
//                       (30, 03)
//                ;
//            """.trimIndent(),
//        ).executeUpdate()
//
//        transaction.commit()
//    }

    @Test
    fun test() {
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)

        val context = JpqlRenderContext()

        val query = jpql {
            select(
                path(Publisher::name),
            ).from(
                entity(Publisher::class),
            )
        }

        val typedQuery = entityManger.createQuery(query, context)

        val result = typedQuery.resultList
        println(result)
    }

    @Test
    fun `the most prolific author`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val context = JpqlRenderContext()
        val jpqlQuery = jpql {
            select(
                path(Author::authorId),
            ).from(
                entity(Author::class),
                join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
            ).groupBy(
                path(Author::authorId),
            ).orderBy(
                count(Author::authorId).desc(),
            )
        }

        // when
        val actual = entityManger.createQuery(jpqlQuery, context).setMaxResults(1).resultList.firstOrNull()

        // then
        assertThat(actual).isEqualTo(1L)
    }

    @Test
    fun `authors who haven't written a book`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val context = JpqlRenderContext()
        val jpqlQuery = jpql {
            select(
                path(Author::authorId),
            ).from(
                entity(Author::class),
                leftJoin(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
            ).where(
                path(BookAuthor::authorId).isNull(),
            ).orderBy(
                path(Author::authorId).asc(),
            )
        }

        // when
        val actual = entityManger.createQuery(jpqlQuery, context).resultList

        // then
        assertThat(actual).isEqualTo(listOf(4L))
    }

    @Test
    fun books() {
        // given
//        val pageable = PageRequest.of(1, 3, Sort.by(Sort.Direction.ASC, "isbn"))

        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val context = JpqlRenderContext()
        val jpqlQuery = jpql {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            )
        }

        // when
        val actual = entityManger.createQuery(jpqlQuery, context).resultList

        // then
        assertThat(actual).isEqualTo(listOf(Isbn("04"), Isbn("05"), Isbn("06")))
    }

    @Test
    fun `the book with the most authors`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val context = JpqlRenderContext()
        val jpqlQuery = jpql {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
                join(Book::authors),
            ).groupBy(
                path(Book::isbn),
            ).orderBy(
                count(Book::isbn).desc(),
            )
        }

        // when
        // SELECT t0.ISBN AS a1 FROM book t0, book_author t1 WHERE (t1.isbn = t0.ISBN) GROUP BY t0.ISBN ORDER BY COUNT(t0.ISBN) DESC LIMIT ? OFFSET ?
        val actual = entityManger.createQuery(jpqlQuery, context).setMaxResults(1).resultList.firstOrNull()

        // then
        assertThat(actual).isEqualTo(Isbn("01"))
    }

//    @Test
//    fun `the most expensive book`() {
//        // given
//        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
//        val context = JpqlRenderContext()
//        val jpqlQuery = jpql {
//            select(
//                path(Book::isbn),
//            ).from(
//                entity(Book::class),
//            ).orderBy(
//                path(Book::salePrice).desc(),
//                path(Book::isbn).asc(),
//            )
//        }
//
//        // when
//        val actual = entityManger.createQuery(jpqlQuery, context).setMaxResults(1).resultList.firstOrNull()
//
//        // then
//        assertThat(actual).isEqualTo(Isbn("10"))
//    }

    //
//    @Test
//    fun `the most recently published book`() {
//        // when
//        val actual = bookRepository.findFirst {
//            select(
//                path(Book::isbn),
//            ).from(
//                entity(Book::class),
//            ).orderBy(
//                path(Book::salePrice).desc(),
//                path(Book::isbn).asc(),
//            )
//        }
//
//        // then
//        assertThat(actual).isEqualTo(Isbn("10"))
//    }
//
//    @Test
//    fun `books published between January and June 2023`() {
//        // when
//        val actual = bookRepository.findAll {
//            select(
//                path(Book::isbn),
//            ).from(
//                entity(Book::class),
//            ).where(
//                path(Book::publishDate).between(
//                    OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
//                    OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
//                ),
//            ).orderBy(
//                path(Book::isbn).asc(),
//            )
//        }
//
//        // then
//        assertThat(actual).isEqualTo(
//            listOf(
//                Isbn("01"),
//                Isbn("02"),
//                Isbn("03"),
//                Isbn("04"),
//                Isbn("05"),
//                Isbn("06"),
//            ),
//        )
//    }
//
//    @Test
//    fun `the book with the biggest discounts`() {
//        // when
//        val actual = bookRepository.findFirst {
//            select(
//                path(Book::isbn),
//            ).from(
//                entity(Book::class),
//            ).orderBy(
//                path(Book::price)(BookPrice::value).minus(path(Book::salePrice)(BookPrice::value)).desc(),
//            )
//        }
//
//        // then
//        assertThat(actual).isEqualTo(Isbn("12"))
//    }
//
    @Test
    fun `employees without a nickname`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val context = JpqlRenderContext()
        val jpqlQuery = jpql {
            select(
                path(Employee::employeeId),
            ).from(
                entity(Employee::class),
            ).where(
                path(Employee::nickname).isNull(),
            ).orderBy(
                path(Employee::employeeId).asc(),
            )
        }

        // when
        val actual = entityManger.createQuery(jpqlQuery, context).resultList

        // then
        assertThat(actual).isEqualTo(
            listOf(
                1L,
                2L,
                3L,
                4L,
                5L,
                6L,
                7L,
                16L,
                17L,
                18L,
                19L,
                20L,
                21L,
                22L,
                23L,
            ),
        )
    }

    @Test
    fun the_number_of_employees_per_department() {
        // given
        data class Row(
            val departmentId: Long,
            val count: Long,
        )

        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val context = JpqlRenderContext()
        val jpqlQuery = jpql {
            selectNew<Row>(
                path(EmployeeDepartment::departmentId),
                count(Employee::employeeId),
            ).from(
                entity(Employee::class),
                join(Employee::departments),
            ).groupBy(
                path(EmployeeDepartment::departmentId),
            ).orderBy(
                path(EmployeeDepartment::departmentId).asc(),
            )
        }

        // when
        val actual = entityManger.createQuery(jpqlQuery, context).resultList

        // then
        assertThat(actual).isEqualTo(
            listOf(
                Row(1, 6),
                Row(2, 15),
                Row(3, 18),
            ),
        )
    }

    @Test
    fun `the number of employees who belong to more than one department`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val context = JpqlRenderContext()
        val jpqlQuery = jpql {
            select(
                count(Employee::employeeId),
            ).from(
                entity(Employee::class),
            ).where(
                path(Employee::employeeId).`in`(
                    select(
                        path(Employee::employeeId),
                    ).from(
                        entity(Employee::class),
                        join(Employee::departments),
                    ).groupBy(
                        path(Employee::employeeId),
                    ).having(
                        count(Employee::employeeId).greaterThan(1L),
                    ).asSubquery(),
                ),
            )
        }

        // when
        val actual = entityManger.createQuery(jpqlQuery, context).resultList

        // then
        assertThat(actual).isEqualTo(listOf(7L))
    }
}
