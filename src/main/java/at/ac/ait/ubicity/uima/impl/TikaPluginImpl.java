package at.ac.ait.ubicity.uima.impl;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;
import net.xeoh.plugins.base.annotations.events.Shutdown;

import org.apache.log4j.Logger;

import at.ac.ait.ubicity.commons.broker.BrokerConsumer;
import at.ac.ait.ubicity.commons.broker.BrokerProducer;
import at.ac.ait.ubicity.commons.broker.events.EventEntry;
import at.ac.ait.ubicity.commons.exceptions.UbicityBrokerException;
import at.ac.ait.ubicity.commons.util.PropertyLoader;
import at.ac.ait.ubicity.uima.TikaPlugin;

@PluginImplementation
public class TikaPluginImpl extends BrokerConsumer implements TikaPlugin {

	private String name;
	private static Logger logger = Logger.getLogger(TikaPluginImpl.class);

	private Producer producer;

	class Producer extends BrokerProducer {
		public Producer(PropertyLoader config) throws UbicityBrokerException {
			super.init(config.getString("plugin.tika.broker.user"), config.getString("plugin.tika.broker.pwd"));
		}
	}

	@Override
	@Init
	public void init() {
		PropertyLoader config = new PropertyLoader(TikaPluginImpl.class.getResource("/analytics.cfg"));
		this.name = config.getString("plugin.tika.name");

		try {
			super.init(config.getString("plugin.tika.broker.user"), config.getString("plugin.tika.broker.pwd"));

			setConsumer(this, config.getString("plugin.tika.broker.consumer"));
			producer = new Producer(config);

		} catch (Exception e) {
			logger.error("During init caught exc.", e);
		}

		logger.info(name + " loaded");
	}

	@Override
	@Shutdown
	public void shutdown() {
		super.shutdown();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void onReceived(String destination, EventEntry event) {
		try {
			producer.publish(event);
		} catch (UbicityBrokerException e) {
			logger.error("Could not publish to CB: ", e);
		}
	}

	@Override
	protected void onReceivedRaw(String destination, String tmsg) {
		throw new UnsupportedOperationException("Not supported.");
	}
}