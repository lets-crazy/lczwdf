package com.trs.ai.ty.constant;

/**
 * @author xholee
 */
public class ErrorMessages {

  private ErrorMessages() {}

  public static final String INVALID_PARAMETER = "参数不合法！";

  public static final String BAD_REQUEST = "非法请求";

  public static final String SYSTEM_ERROR = "系统内部错误";

  public static final String DB_ERROR = "数据库异常";

  public static final String INVALID_ROLE = "权限不合法! ";

  public static final String FIELD_MAPPER_ERROR = "添加失败，“[%s]规则”配置中系统属性“[%s]”未与当前数据源属性建立映射关系，规则无法生效!";

  public static final String UNAUTHORIZED_ROLE = "权限不合法，或者登录超时，请重新登录！";

  public static final String AUTHORIZATION_ERRO = "权限认证失败";

  public static final String USER_LOCKED = "账户被锁定";

  public static final String UNAUTHORIZED_MODULE = "无该操作权限，请联系管理员添加！";

  public static final String TRANSFER_FILE_FAIL = "传输文件失败！";
}
