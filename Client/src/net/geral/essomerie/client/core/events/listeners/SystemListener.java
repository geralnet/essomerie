package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

import net.geral.essomerie.shared.BuildInfo;
import net.geral.essomerie.shared.communication.MessageData;

public interface SystemListener extends EventListener {
  public void systemConnected();

  public void systemConnecting(String serverAddress, int serverPort);

  public void systemConnectionFailed(boolean willTryAgain);

  public void systemConnectionTryAgainCountdown(int tryAgainCountdown);

  public void systemInformSent(MessageData md);

  public void systemLoggedOut();

  public void systemLoginAccepted();

  public void systemLoginFailed();

  public void systemPongReceived(long lag);

  public void systemVersionReceived(BuildInfo version);
}
