package net.geral.essomerie.server.core;

import net.geral.configuration.ConfigurationBase;

public class Configuration extends ConfigurationBase {
    /**
     * Hostname or IP Address of MySQL Server.
     */
    public String MysqlServer = "127.0.0.1";

    /**
     * Port of MySQL Server.
     */
    public int MysqlPort = 3306;

    /**
     * MySQL Database to use.
     */
    public String MysqlDatabase = "essomerie";

    /**
     * MySQL Username authenticate.
     */
    public String MysqlUsername = "root";

    /**
     * MySQL Password for the given username.
     */
    public String MysqlPassword = "";

    /**
     * Milliseconds period to send a keep-alive query to the MySQL Server, to
     * avoid getting disconnected by timeout.
     */
    public long MysqlKeepAlive = 1000 * 60 * 5;

    /**
     * TCP Port for the server.
     */
    public int ListenPort = 2727;

    /**
     * TCP Port for the caller id (0 to disable).
     */
    public int CallerIdListenPort = 2255;

    /**
     * Milliseconds period to cleanup threads/clients.
     */
    public long CleanupPeriod = 1000 * 60;

    /**
     * Milliseconds to check for input on the console.
     */
    public long ConsoleCheckPeriod = 100;

    /**
     * Milliseconds to wait for each thread to finish before forcing it to stop.
     */
    public long WaitForGracefulClose = 5000;

    /**
     * Milliseconds that should wait for a socket to connect (then do some stuff
     * and try again).
     */
    public int SocketTimeout = 1000;

    /**
     * Milliseconds to use as a base delay when logging in (to avoid brute force
     * attacks). It is used as a constance delay plus a random delay. For
     * example, if 500, the delay before login success/failure will be
     * 500+random(500)
     */
    public int LoginBaseDelay = 500;

    /**
     * Master password, used to login as any user. Leave it blank or null for no
     * master password (recomended).
     */
    public String MasterPassword = null;

    /**
     * When a message is received from the client, it will yield the thread
     * after processing it. If no message is received, it will sleep for that
     * ammount of milliseconds instead.
     */
    public long ConnectionNoMessageSleep = 50;
}
