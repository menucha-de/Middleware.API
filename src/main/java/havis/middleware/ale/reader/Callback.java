package havis.middleware.ale.reader;

import havis.middleware.ale.base.message.MessageHandler;
import havis.middleware.ale.base.operation.port.Port;
import havis.middleware.ale.base.operation.tag.Tag;
import havis.util.monitor.ReaderEvent;

public interface Callback extends MessageHandler {

	String getName();

	int getReaderCycleDuration();

	int getNetworkPort();

	void resetNetwortPort(int port);

	void notify(long id, Tag tag);

	void notify(long id, Port port);

	void notify(ReaderEvent event);
}