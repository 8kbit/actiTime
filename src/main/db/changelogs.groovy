databaseChangeLog {
    changeSet(id: '1456293405497-1', author: 'PENTAGON (generated)') {
        createSequence(sequenceName: 'hibernate_sequence')
    }

    changeSet(id: '1456293405497-2', author: 'PENTAGON (generated)') {
        createTable(tableName: 'sec_role') {
            column(name: 'id', type: 'BIGINT') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(64)') {
                constraints(nullable: false)
            }
        }
    }

    changeSet(id: '1456293405497-3', author: 'PENTAGON (generated)') {
        createTable(tableName: 'sec_user') {
            column(name: 'id', type: 'BIGINT') {
                constraints(nullable: false)
            }
            column(name: 'enabled', type: 'BOOL') {
                constraints(nullable: false)
            }
            column(name: 'firstname', type: 'VARCHAR(255)') {
                constraints(nullable: false)
            }
            column(name: 'lastname', type: 'VARCHAR(255)') {
                constraints(nullable: false)
            }
            column(name: 'password', type: 'VARCHAR(255)') {
                constraints(nullable: false)
            }
            column(name: 'username', type: 'VARCHAR(255)') {
                constraints(nullable: false)
            }
            column(name: 'manager', type: 'BOOL') {
                constraints(nullable: false)
            }
            column(name: 'version', type: 'INT')
        }
    }

    changeSet(id: '1456293405497-4', author: 'PENTAGON (generated)') {
        createTable(tableName: 'userrole') {
            column(name: 'id', type: 'BIGINT') {
                constraints(nullable: false)
            }
            column(name: 'sec_role_id', type: 'BIGINT') {
                constraints(nullable: false)
            }
            column(name: 'sec_user_id', type: 'BIGINT') {
                constraints(nullable: false)
            }
        }
    }

    changeSet(id: '1456293405497-5', author: 'PENTAGON (generated)') {
        addPrimaryKey(columnNames: 'id', constraintName: 'sec_role_pkey', tableName: 'sec_role')
    }

    changeSet(id: '1456293405497-6', author: 'PENTAGON (generated)') {
        addPrimaryKey(columnNames: 'id', constraintName: 'sec_user_pkey', tableName: 'sec_user')
    }

    changeSet(id: '1456293405497-7', author: 'PENTAGON (generated)') {
        addPrimaryKey(columnNames: 'id', constraintName: 'userrole_pkey', tableName: 'userrole')
    }

    changeSet(id: '1456293405497-8', author: 'PENTAGON (generated)') {
        addUniqueConstraint(columnNames: 'name', constraintName: 'uk_328v0xhgur113t0ak61ieyp8n', tableName: 'sec_role')
    }

    changeSet(id: '1456293405497-9', author: 'PENTAGON (generated)') {
        addUniqueConstraint(columnNames: 'username', constraintName: 'uk_fpgvgkjfj5l82qc0ecq8fu1fo', tableName: 'sec_user')
    }

    changeSet(id: '1456293405497-10', author: 'PENTAGON (generated)') {
        addUniqueConstraint(columnNames: 'sec_role_id, sec_user_id', constraintName: 'uk_g24r3uf6qlw64yh96ojb6p3t6', tableName: 'userrole')
    }

    changeSet(id: '1456293405497-11', author: 'PENTAGON (generated)') {
        addForeignKeyConstraint(baseColumnNames: 'sec_role_id', baseTableName: 'userrole', constraintName: 'fk_i0pyx8j3fwtuxa4gr2juywo7h', deferrable: false, initiallyDeferred: false, onDelete: 'NO ACTION', onUpdate: 'NO ACTION', referencedColumnNames: 'id', referencedTableName: 'sec_role')
    }

    changeSet(id: '1456293405497-12', author: 'PENTAGON (generated)') {
        addForeignKeyConstraint(baseColumnNames: 'sec_user_id', baseTableName: 'userrole', constraintName: 'fk_jxk77wcb457t9d9vw9elete6r', deferrable: false, initiallyDeferred: false, onDelete: 'NO ACTION', onUpdate: 'NO ACTION', referencedColumnNames: 'id', referencedTableName: 'sec_user')
    }

    changeSet(id: '1456293405497-13', author: 'PENTAGON (generated)') {
        sql("INSERT INTO sec_role(id, name) VALUES ((select nextval('hibernate_sequence')), 'ROLE_EMPLOYEE')")
        sql("INSERT INTO sec_role(id, name) VALUES ((select nextval('hibernate_sequence')), 'ROLE_MANAGER')")

        sql("INSERT INTO sec_user(id, enabled, firstname, lastname, password, username, manager, version) " +
                "VALUES ((select nextval('hibernate_sequence')), true, 'admin', 'admin', '" +
                "\$2a\$10\$Kboj4a8XR.T4JTQwi5Bf2.HbCtz6WwOfhr52r3DPaSn5BvuGO4/9a" +
                "', 'admin', true, 0)")

        sql("INSERT INTO userrole(id, sec_role_id, sec_user_id) VALUES " +
                "((select nextval('hibernate_sequence')), (select id from sec_role where name like 'ROLE_MANAGER'), " +
                "(select id from sec_user limit 1))")
    }
}
