package com.zhuhuibao.repository.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Handler<T> {
	T handle(ResultSet rs) throws SQLException;
}
