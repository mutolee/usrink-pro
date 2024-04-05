package ink.usr.framework.mysql.enums;

public enum Ds {

    /**
     * 写模式
     */
    W,

    /**
     * 读模式，如果没有配置读库，则默认为写库
     */
    R;

}
