package havis.middleware.ale.exit;

import java.util.logging.Logger;

public class Exits {

	public final static Logger Log = Logger.getLogger(Exits.class.getName());

	public static class Level {

		public final static java.util.logging.Level Error = java.util.logging.Level.SEVERE;

		public final static java.util.logging.Level Warning = java.util.logging.Level.WARNING;

		public final static java.util.logging.Level Information = java.util.logging.Level.INFO;

		public final static java.util.logging.Level Detail = java.util.logging.Level.FINE;
	}

	private static String fqPrefix = Exits.class.getName() + ".";

	private static String getName(Class<?> clazz) {
		String fq = clazz.getName();
		return fq.substring(fqPrefix.length(), fq.length()).replace('$', '.');
	}

	public static class Common {

		public final static String Name = getName(Common.class);

		public final static String Error = "Common.Error";

		public final static String Warning = "Common.Warning";

		public final static String Information = "Common.Information";
	}

	public static class Service {

		public final static String Name = getName(Service.class);

		public static class LR {

			public final static String Name = getName(LR.class);

			public final static String Define = "Service.LR.Define";

			public final static String DefineFailed = "Service.LR.DefineFailed";

			public final static String Update = "Service.LR.Update";

			public final static String UpdateFailed = "Service.LR.UpdateFailed";

			public final static String Undefine = "Service.LR.Undefine";

			public final static String UndefineFailed = "Service.LR.UndefineFailed";

			public final static String GetLRSpec = "Service.LR.Get";

			public final static String GetLRSpecFailed = "Service.LR.GetFailed";

			public final static String GetLRSpecNames = "Service.LR.GetNames";

			public final static String AddReaders = "Service.LR.AddReaders";

			public final static String AddReadersFailed = "Service.LR.AddReadersFailed";

			public final static String SetReaders = "Service.LR.SetReaders";

			public final static String SetReadersFailed = "Service.LR.SetReadersFailed";

			public final static String RemoveReaders = "Service.LR.RemoveReaders";

			public final static String RemoveReadersFailed = "Service.LR.RemoveReadersFailed";

			public final static String SetProperties = "Service.LR.SetProperties";

			public final static String SetPropertiesFailed = "Service.LR.SetPropertiesFailed";

			public final static String GetPropertyValue = "Service.LR.GetPropertyValue";

			public final static String GetPropertyValueFailed = "Service.LR.GetPropertyValueFailed";
		}

		public static class TM {

			public final static String Name = getName(TM.class);

			public final static String Define = "Service.TM.Define";

			public final static String DefineFailed = "Service.TM.DefineFailed";

			public final static String Undefine = "Service.TM.Undefine";

			public final static String UndefineFailed = "Service.TM.UndefineFailed";

			public final static String GetTMSpec = "Service.TM.Get";

			public final static String GetTMSpecFailed = "Service.TM.GetFailed";

			public final static String GetTMSpecNames = "Service.TM.GetNames";
		}

		public static class EC {

			public final static String Name = getName(EC.class);

			public final static String Define = "Service.EC.Define";

			public final static String DefineFailed = "Service.EC.DefineFailed";

			public final static String Undefine = "Service.EC.Undefine";

			public final static String UndefineFailed = "Service.EC.UndefineFailed";

			public final static String GetECSpec = "Service.EC.Get";

			public final static String GetECSpecFailed = "Service.EC.GetFailed";

			public final static String GetECSpecNames = "Service.EC.GetNames";

			public final static String Subscribe = "Service.EC.Subscribe";

			public final static String SubscribeFailed = "Service.EC.SubscribeFailed";

			public final static String Unsubscribe = "Service.EC.Unsubscribe";

			public final static String UnsubscribeFailed = "Service.EC.UnsubscribeFailed";

			public final static String Poll = "Service.EC.Poll";

			public final static String PollFailed = "Service.EC.PollFailed";

			public final static String Immediate = "Service.EC.Immediate";

			public final static String ImmediateFailed = "Service.EC.ImmediateFailed";

			public final static String GetSubscribers = "Service.EC.GetSubscribers";

			public final static String GetSubscribersFailed = "Service.EC.GetSubscribersFailed";
		}

		public static class CC {

			public final static String Name = getName(CC.class);

			public final static String Define = "Service.CC.Define";

			public final static String DefineFailed = "Service.CC.DefineFailed";

			public final static String Undefine = "Service.CC.Undefine";

			public final static String UndefineFailed = "Service.CC.UndefineFailed";

			public final static String GetCCSpec = "Service.CC.Get";

			public final static String GetCCSpecFailed = "Service.CC.GetFailed";

			public final static String GetCCSpecNames = "Service.CC.GetNames";

			public final static String Subscribe = "Service.CC.Subscribe";

			public final static String SubscribeFailed = "Service.CC.SubscribeFailed";

			public final static String Unsubscribe = "Service.CC.Unsubscribe";

			public final static String UnsubscribeFailed = "Service.CC.UnsubscribeFailed";

			public final static String Poll = "Service.CC.Poll";

			public final static String PollFailed = "Service.CC.PollFailed";

			public final static String Immediate = "Service.CC.Immediate";

			public final static String ImmediateFailed = "Service.CC.ImmediateFailed";

			public final static String GetSubscribers = "Service.CC.GetSubscribers";

			public final static String GetSubscribersFailed = "Service.CC.GetSubscribersFailed";

			public static class Cache {

				public final static String Name = getName(Cache.class);

				public final static String Define = "Service.CC.Cache.Define";

				public final static String DefineFailed = "Service.CC.Cache.DefineFailed";

				public final static String Undefine = "Service.CC.Cache.Undefine";

				public final static String UndefineFailed = "Service.CC.Cache.UndefineFailed";

				public final static String Get = "Service.CC.Cache.Get";

				public final static String GetFailed = "Service.CC.Cache.GetFailed";

				public final static String GetNames = "Service.CC.Cache.GetNames";

				public final static String Replenish = "Service.CC.Cache.Replenish";

				public final static String ReplenishFailed = "Service.CC.Cache.ReplenishFailed";

				public final static String Deplete = "Service.CC.Cache.Deplete";

				public final static String DepleteFailed = "Service.CC.Cache.DepleteFailed";

				public final static String GetContents = "Service.CC.Cache.GetContents";

				public final static String GetContentsFailed = "Service.CC.Cache.GetContentsFailed";
			}

			public static class Association {

				public final static String Name = getName(Association.class);

				public final static String Define = "Service.CC.Association.Define";

				public final static String DefineFailed = "Service.CC.Association.DefineFailed";

				public final static String Undefine = "Service.CC.Association.Undefine";

				public final static String UndefineFailed = "Service.CC.Association.UndefineFailed";

				public final static String Get = "Service.CC.Association.Get";

				public final static String GetFailed = "Service.CC.Association.GetFailed";

				public final static String GetNames = "Service.CC.Association.GetNames";

				public final static String PutEntries = "Service.CC.Association.PutEntries";

				public final static String PutEntriesFailed = "Service.CC.Association.PutEntriesFailed";

				public final static String GetValue = "Service.CC.Association.GetValue";

				public final static String GetValueFailed = "Service.CC.Association.GetValueFailed";

				public final static String GetEntries = "Service.CC.Association.GetEntries";

				public final static String GetEntriesFailed = "Service.CC.Association.GetEntriesFailed";

				public final static String RemoveEntry = "Service.CC.Association.RemoveEntry";

				public final static String RemoveEntryFailed = "Service.CC.Association.RemoveEntryFailed";

				public final static String RemoveEntries = "Service.CC.Association.RemoveEntries";

				public final static String RemoveEntriesFailed = "Service.CC.Association.RemoveEntriesFailed";
			}

			public static class Random {

				public final static String Name = getName(Random.class);

				public final static String Define = "Service.CC.Random.Define";

				public final static String DefineFailed = "Service.CC.Random.DefineFailed";

				public final static String Undefine = "Service.CC.Random.Undefine";

				public final static String UndefineFailed = "Service.CC.Random.UndefineFailed";

				public final static String Get = "Service.CC.Random.Get";

				public final static String GetFailed = "Service.CC.Random.GetFailed";

				public final static String GetNames = "Service.CC.Random.GetNames";
			}
		}

		public static class PC {

			public final static String Name = getName(PC.class);

			public final static String Define = "Service.PC.Define";

			public final static String DefineFailed = "Service.PC.DefineFailed";

			public final static String Undefine = "Service.PC.Undefine";

			public final static String UndefineFailed = "Service.PC.UndefineFailed";

			public final static String GetPCSpec = "Service.PC.Get";

			public final static String GetPCSpecFailed = "Service.PC.GetFailed";

			public final static String GetPCSpecNames = "Service.PC.GetNames";

			public final static String Subscribe = "Service.PC.Subscribe";

			public final static String SubscribeFailed = "Service.PC.SubscribeFailed";

			public final static String Unsubscribe = "Service.PC.Unsubscribe";

			public final static String UnsubscribeFailed = "Service.PC.UnsubscribeFailed";

			public final static String Poll = "Service.PC.Poll";

			public final static String PollFailed = "Service.PC.PollFailed";

			public final static String Immediate = "Service.PC.Immediate";

			public final static String ImmediateFailed = "Service.PC.ImmediateFailed";

			public final static String GetSubscribers = "Service.PC.GetSubscribers";

			public final static String GetSubscribersFailed = "Service.PC.GetSubscribersFailed";

			public final static String Execute = "Service.PC.Execute";

			public final static String ExecuteFailed = "Service.PC.ExecuteFailed";
		}
	}

	public static class Core {

		public final static String Name = getName(Core.class);

		public static class Cycle {

			public final static String Name = getName(Cycle.class);

			public static class EventCycle {

				public final static String Name = getName(EventCycle.class);

				public final static String State = "Core.Cycle.EventCycle.State";

				public static class Trigger {

					public final static String Name = getName(Trigger.class);

					public final static String Start = "Core.Cycle.EventCycle.Trigger.Start";

					public final static String Stop = "Core.Cycle.EventCycle.Trigger.Stop";
				}

				public final static String Begin = "Core.Cycle.EventCycle.Begin";

				public final static String Notify = "Core.Cycle.EventCycle.Notify";

				public final static String Filter = "Core.Cycle.EventCycle.Filter";

				public final static String Evaluate = "Core.Cycle.EventCycle.Evaluate";

				public final static String Report = "Core.Cycle.EventCycle.Report";

				public final static String Deliver = "Core.Cycle.EventCycle.Deliver";

				public final static String Error = "Core.Cycle.EventCycle.Error";
			}

			public static class CommandCycle {

				public final static String Name = getName(CommandCycle.class);

				public final static String State = "Core.Cycle.CommandCycle.State";

				public static class Trigger {

					public final static String Name = getName(Trigger.class);

					public final static String Start = "Core.Cycle.CommandCycle.Trigger.Start";

					public final static String Stop = "Core.Cycle.CommandCycle.Trigger.Stop";
				}

				public final static String Begin = "Core.Cycle.CommandCycle.Begin";

				public final static String Notify = "Core.Cycle.CommandCycle.Notify";

				public final static String Filter = "Core.Cycle.CommandCycle.Filter";

				public final static String Execute = "Core.Cycle.CommandCycle.Execute";

				public final static String Executed = "Core.Cycle.CommandCycle.Executed";

				public final static String Evaluate = "Core.Cycle.CommandCycle.Evaluate";

				public final static String Report = "Core.Cycle.CommandCycle.Report";

				public final static String Deliver = "Core.Cycle.CommandCycle.Deliver";

				public final static String Error = "Core.Cycle.CommandCycle.Error";
			}

			public static class PortCycle {

				public final static String Name = getName(PortCycle.class);

				public final static String State = "Core.Cycle.PortCycle.State";

				public static class Trigger {

					public final static String Name = getName(Trigger.class);

					public final static String Start = "Core.Cycle.PortCycle.Trigger.Start";

					public final static String Stop = "Core.Cycle.PortCycle.Trigger.Stop";
				}

				public final static String Begin = "Core.Cycle.PortCycle.Begin";

				public final static String Notify = "Core.Cycle.PortCycle.Notify";

				public final static String Filter = "Core.Cycle.PortCycle.Filter";

				public final static String Execute = "Core.Cycle.PortCycle.Execute";

				public final static String Evaluate = "Core.Cycle.PortCycle.Evaluate";

				public final static String Report = "Core.Cycle.PortCycle.Report";

				public final static String Deliver = "Core.Cycle.PortCycle.Deliver";

				public final static String Error = "Core.Cycle.PortCycle.Error";
			}
		}
	}

	public static class Subscriber {

		public final static String Name = getName(Subscriber.class);

		public static class Controller {

			public final static String Name = getName(Controller.class);

			public final static String Exited = "Subscriber.Controller.Exited";

			public final static String Faulted = "Subscriber.Controller.Faulted";

			public final static String ReinitFailed = "Subscriber.Controller.ReinitFailed";

			public final static String DeliverFailed = "Subscriber.Controller.DeliverFailed";
		}

		public static class Container {

			public final static String Name = getName(Container.class);

			public final static String DeliverFailed = "Subscriber.Container.DeliverFailed";

			public final static String DeliverAborted = "Subscriber.Container.DeliverAborted";

			public final static String ThresholdExceeded = "Subscriber.Container.ThresholdExceeded";

			public final static String ThresholdEased = "Subscriber.Container.ThresholdEased";

			public final static String ReportsLost = "Subscriber.Container.ReportsLost";

			public final static String InvokeFailed = "Subscriber.Container.InvokeFailed";
		}
	}

	public static class Reader {

		public final static String Name = getName(Reader.class);

		public static class Controller {

			public final static String Name = getName(Controller.class);

			public final static String ExecutedTag = "Reader.Controller.ExecutedTag";

			public final static String Exited = "Reader.Controller.Exited";

			public final static String Faulted = "Reader.Controller.Faulted";

			public final static String ReinitFailed = "Reader.Controller.ReinitFailed";

			public final static String ConnectFailed = "Reader.Controller.ConnectFailed";

			public final static String ConnectionLost = "Reader.Controller.ConnectionLost";

			public final static String Reconnect = "Reader.Controller.Reconnect";

			public final static String ReconnectFailed = "Reader.Controller.ReconnectFailed";

			public final static String ReconnectAborted = "Reader.Controller.ReconnectAborted";

			public final static String Error = "Reader.Controller.Error";

			public final static String Warning = "Reader.Controller.Warning";

			public final static String Information = "Reader.Controller.Information";

			public static class Callback {

				public final static String Name = getName(Callback.class);

				public final static String NotifyTag = "Reader.Controller.Callback.NotifyTag";

				public final static String NotifyPort = "Reader.Controller.Callback.NotifyPort";

				public final static String NotifyMessage = "Reader.Controller.Callback.NotifyMessage";
			}
		}
	}

	/**
	 * Defines all exit types.
	 */
	public static String[] All = new String[] {

		Common.Error, Common.Warning, Common.Information,

		Service.LR.Define, Service.LR.DefineFailed, Service.LR.Update, Service.LR.UpdateFailed, Service.LR.Undefine, Service.LR.UndefineFailed,
		Service.LR.GetLRSpec, Service.LR.GetLRSpecFailed, Service.LR.GetLRSpecNames, Service.LR.AddReaders, Service.LR.AddReadersFailed, Service.LR.SetReaders,
		Service.LR.SetReadersFailed, Service.LR.RemoveReaders, Service.LR.RemoveReadersFailed, Service.LR.SetProperties, Service.LR.SetPropertiesFailed,
		Service.LR.GetPropertyValue, Service.LR.GetPropertyValueFailed,

		Service.TM.Define, Service.TM.DefineFailed, Service.TM.Undefine, Service.TM.UndefineFailed, Service.TM.GetTMSpec, Service.TM.GetTMSpecFailed,
		Service.TM.GetTMSpecNames,

		Service.EC.Define, Service.EC.DefineFailed, Service.EC.Undefine, Service.EC.UndefineFailed, Service.EC.GetECSpec, Service.EC.GetECSpecFailed,
		Service.EC.GetECSpecNames, Service.EC.Subscribe, Service.EC.SubscribeFailed, Service.EC.Unsubscribe, Service.EC.UnsubscribeFailed, Service.EC.Poll,
		Service.EC.PollFailed, Service.EC.Immediate, Service.EC.ImmediateFailed, Service.EC.GetSubscribers, Service.EC.GetSubscribersFailed,

		Service.CC.Define, Service.CC.DefineFailed, Service.CC.Undefine, Service.CC.UndefineFailed, Service.CC.GetCCSpec, Service.CC.GetCCSpecFailed,
		Service.CC.GetCCSpecNames, Service.CC.Subscribe, Service.CC.SubscribeFailed, Service.CC.Unsubscribe, Service.CC.UnsubscribeFailed, Service.CC.Poll,
		Service.CC.PollFailed, Service.CC.Immediate, Service.CC.ImmediateFailed, Service.CC.GetSubscribers, Service.CC.GetSubscribersFailed,

		Service.CC.Cache.Define, Service.CC.Cache.DefineFailed, Service.CC.Cache.Undefine, Service.CC.Cache.UndefineFailed, Service.CC.Cache.Get,
		Service.CC.Cache.GetFailed, Service.CC.Cache.GetNames, Service.CC.Cache.Replenish, Service.CC.Cache.ReplenishFailed, Service.CC.Cache.Deplete,
		Service.CC.Cache.DepleteFailed, Service.CC.Cache.GetContents, Service.CC.Cache.GetContentsFailed,

		Service.CC.Association.Define, Service.CC.Association.DefineFailed, Service.CC.Association.Undefine, Service.CC.Association.UndefineFailed,
		Service.CC.Association.Get, Service.CC.Association.GetFailed, Service.CC.Association.GetNames, Service.CC.Association.PutEntries,
		Service.CC.Association.PutEntriesFailed, Service.CC.Association.GetValue, Service.CC.Association.GetValueFailed, Service.CC.Association.GetEntries,
		Service.CC.Association.GetEntriesFailed, Service.CC.Association.RemoveEntry, Service.CC.Association.RemoveEntryFailed,
		Service.CC.Association.RemoveEntries, Service.CC.Association.RemoveEntriesFailed,

		Service.CC.Random.Define, Service.CC.Random.DefineFailed, Service.CC.Random.Undefine, Service.CC.Random.UndefineFailed, Service.CC.Random.Get,
		Service.CC.Random.GetFailed, Service.CC.Random.GetNames,

		Service.PC.Define, Service.PC.DefineFailed, Service.PC.Undefine, Service.PC.UndefineFailed, Service.PC.GetPCSpec, Service.PC.GetPCSpecFailed,
		Service.PC.GetPCSpecNames, Service.PC.Subscribe, Service.PC.SubscribeFailed, Service.PC.Unsubscribe, Service.PC.UnsubscribeFailed, Service.PC.Poll,
		Service.PC.PollFailed, Service.PC.Immediate, Service.PC.ImmediateFailed, Service.PC.GetSubscribers, Service.PC.GetSubscribersFailed,
		Service.PC.Execute, Service.PC.ExecuteFailed,

		Core.Cycle.EventCycle.State, Core.Cycle.EventCycle.Trigger.Start, Core.Cycle.EventCycle.Trigger.Stop, Core.Cycle.EventCycle.Begin,
		Core.Cycle.EventCycle.Notify, Core.Cycle.EventCycle.Filter, Core.Cycle.EventCycle.Evaluate, Core.Cycle.EventCycle.Report,
		Core.Cycle.EventCycle.Deliver, Core.Cycle.EventCycle.Error,

		Core.Cycle.CommandCycle.State, Core.Cycle.CommandCycle.Trigger.Start, Core.Cycle.CommandCycle.Trigger.Stop, Core.Cycle.CommandCycle.Begin,
		Core.Cycle.CommandCycle.Notify, Core.Cycle.CommandCycle.Filter, Core.Cycle.CommandCycle.Evaluate, Core.Cycle.CommandCycle.Execute,
		Core.Cycle.CommandCycle.Executed, Core.Cycle.CommandCycle.Report, Core.Cycle.CommandCycle.Deliver, Core.Cycle.CommandCycle.Error,

		Core.Cycle.PortCycle.State, Core.Cycle.PortCycle.Trigger.Start, Core.Cycle.PortCycle.Trigger.Stop, Core.Cycle.PortCycle.Begin,
		Core.Cycle.PortCycle.Notify, Core.Cycle.PortCycle.Filter, Core.Cycle.PortCycle.Execute, Core.Cycle.PortCycle.Evaluate, Core.Cycle.PortCycle.Report,
		Core.Cycle.PortCycle.Deliver, Core.Cycle.PortCycle.Error,

		Subscriber.Controller.Exited, Subscriber.Controller.Faulted, Subscriber.Controller.ReinitFailed,

		Subscriber.Container.DeliverFailed, Subscriber.Container.DeliverAborted, Subscriber.Container.ThresholdExceeded, Subscriber.Container.ThresholdEased,
		Subscriber.Container.ReportsLost, Subscriber.Container.InvokeFailed,

		Reader.Controller.ExecutedTag, Reader.Controller.Exited, Reader.Controller.Faulted, Reader.Controller.ReinitFailed, Reader.Controller.ConnectFailed,
		Reader.Controller.ConnectionLost, Reader.Controller.Reconnect, Reader.Controller.ReconnectFailed, Reader.Controller.ReconnectAborted,

		Reader.Controller.Error, Reader.Controller.Warning, Reader.Controller.Information,

		Reader.Controller.Callback.NotifyTag, Reader.Controller.Callback.NotifyPort, Reader.Controller.Callback.NotifyMessage
	};

	/**
	 * Defines all exit types with tracing level.
	 */
	public static String[] Tracing = new String[] {

		Common.Error, Common.Warning, Common.Information,

		Service.LR.Define, Service.LR.DefineFailed, Service.LR.Update, Service.LR.UpdateFailed, Service.LR.Undefine, Service.LR.UndefineFailed,
		Service.LR.GetLRSpec, Service.LR.GetLRSpecFailed, Service.LR.GetLRSpecNames, Service.LR.AddReaders, Service.LR.AddReadersFailed, Service.LR.SetReaders,
		Service.LR.SetReadersFailed, Service.LR.RemoveReaders, Service.LR.RemoveReadersFailed, Service.LR.SetProperties, Service.LR.SetPropertiesFailed,
		Service.LR.GetPropertyValue, Service.LR.GetPropertyValueFailed,

		Service.TM.Define, Service.TM.DefineFailed, Service.TM.Undefine, Service.TM.UndefineFailed, Service.TM.GetTMSpec, Service.TM.GetTMSpecFailed,
		Service.TM.GetTMSpecNames,

		Service.EC.Define, Service.EC.DefineFailed, Service.EC.Undefine, Service.EC.UndefineFailed, Service.EC.GetECSpec, Service.EC.GetECSpecFailed,
		Service.EC.GetECSpecNames, Service.EC.Subscribe, Service.EC.SubscribeFailed, Service.EC.Unsubscribe, Service.EC.UnsubscribeFailed, Service.EC.Poll,
		Service.EC.PollFailed, Service.EC.Immediate, Service.EC.ImmediateFailed, Service.EC.GetSubscribers, Service.EC.GetSubscribersFailed,

		Service.CC.Define, Service.CC.DefineFailed, Service.CC.Undefine, Service.CC.UndefineFailed, Service.CC.GetCCSpec, Service.CC.GetCCSpecFailed,
		Service.CC.GetCCSpecNames, Service.CC.Subscribe, Service.CC.SubscribeFailed, Service.CC.Unsubscribe, Service.CC.UnsubscribeFailed, Service.CC.Poll,
		Service.CC.PollFailed, Service.CC.Immediate, Service.CC.ImmediateFailed, Service.CC.GetSubscribers, Service.CC.GetSubscribersFailed,

		Service.CC.Cache.Define, Service.CC.Cache.DefineFailed, Service.CC.Cache.Undefine, Service.CC.Cache.UndefineFailed, Service.CC.Cache.Get,
		Service.CC.Cache.GetFailed, Service.CC.Cache.GetNames, Service.CC.Cache.Replenish, Service.CC.Cache.ReplenishFailed, Service.CC.Cache.Deplete,
		Service.CC.Cache.DepleteFailed, Service.CC.Cache.GetContents, Service.CC.Cache.GetContentsFailed,

		Service.CC.Association.Define, Service.CC.Association.DefineFailed, Service.CC.Association.Undefine, Service.CC.Association.UndefineFailed,
		Service.CC.Association.Get, Service.CC.Association.GetFailed, Service.CC.Association.GetNames, Service.CC.Association.PutEntries,
		Service.CC.Association.PutEntriesFailed, Service.CC.Association.GetValue, Service.CC.Association.GetValueFailed, Service.CC.Association.GetEntries,
		Service.CC.Association.GetEntriesFailed, Service.CC.Association.RemoveEntry, Service.CC.Association.RemoveEntryFailed,
		Service.CC.Association.RemoveEntries, Service.CC.Association.RemoveEntriesFailed,

		Service.CC.Random.Define, Service.CC.Random.DefineFailed, Service.CC.Random.Undefine, Service.CC.Random.UndefineFailed, Service.CC.Random.Get,
		Service.CC.Random.GetFailed, Service.CC.Random.GetNames,

		Service.PC.Define, Service.PC.DefineFailed, Service.PC.Undefine, Service.PC.UndefineFailed, Service.PC.GetPCSpec, Service.PC.GetPCSpecFailed,
		Service.PC.GetPCSpecNames, Service.PC.Subscribe, Service.PC.SubscribeFailed, Service.PC.Unsubscribe, Service.PC.UnsubscribeFailed, Service.PC.Poll,
		Service.PC.PollFailed, Service.PC.Immediate, Service.PC.ImmediateFailed, Service.PC.GetSubscribers, Service.PC.GetSubscribersFailed,
		Service.PC.Execute, Service.PC.ExecuteFailed,

		Core.Cycle.EventCycle.State, Core.Cycle.EventCycle.Trigger.Start, Core.Cycle.EventCycle.Trigger.Stop, Core.Cycle.EventCycle.Begin,
		Core.Cycle.EventCycle.Filter, Core.Cycle.EventCycle.Evaluate, Core.Cycle.EventCycle.Report, Core.Cycle.EventCycle.Deliver, Core.Cycle.EventCycle.Error,

		Core.Cycle.CommandCycle.State, Core.Cycle.CommandCycle.Trigger.Start, Core.Cycle.CommandCycle.Trigger.Stop, Core.Cycle.CommandCycle.Begin,
		Core.Cycle.CommandCycle.Filter, Core.Cycle.CommandCycle.Evaluate, Core.Cycle.CommandCycle.Execute, Core.Cycle.CommandCycle.Report,
		Core.Cycle.CommandCycle.Deliver, Core.Cycle.CommandCycle.Error,

		Core.Cycle.PortCycle.State, Core.Cycle.PortCycle.Trigger.Start, Core.Cycle.PortCycle.Trigger.Stop, Core.Cycle.PortCycle.Begin,
		Core.Cycle.PortCycle.Filter, Core.Cycle.PortCycle.Execute, Core.Cycle.PortCycle.Evaluate, Core.Cycle.PortCycle.Report, Core.Cycle.PortCycle.Deliver,
		Core.Cycle.PortCycle.Error,

		Subscriber.Controller.Exited, Subscriber.Controller.Faulted, Subscriber.Controller.ReinitFailed,

		Subscriber.Container.DeliverFailed, Subscriber.Container.DeliverAborted, Subscriber.Container.ThresholdExceeded, Subscriber.Container.ThresholdEased,
		Subscriber.Container.ReportsLost, Subscriber.Container.InvokeFailed,

		Reader.Controller.Exited, Reader.Controller.Faulted, Reader.Controller.ReinitFailed, Reader.Controller.ConnectFailed, Reader.Controller.ConnectionLost,
		Reader.Controller.Reconnect, Reader.Controller.ReconnectFailed, Reader.Controller.ReconnectAborted, Reader.Controller.Error, Reader.Controller.Warning,
		Reader.Controller.Information
	};

	/**
	 * Defines all exit types with information level.
	 */
	public static String[] Information = new String[] {

		Common.Error, Common.Warning, Common.Information,

		Core.Cycle.EventCycle.Error,

		Core.Cycle.CommandCycle.Error,

		Core.Cycle.PortCycle.Error,

		Subscriber.Controller.Exited, Subscriber.Controller.Faulted, Subscriber.Controller.ReinitFailed,

		Subscriber.Container.DeliverFailed, Subscriber.Container.DeliverAborted, Subscriber.Container.ThresholdExceeded, Subscriber.Container.ThresholdEased,
		Subscriber.Container.ReportsLost, Subscriber.Container.InvokeFailed,

		Reader.Controller.Exited, Reader.Controller.Faulted, Reader.Controller.ReinitFailed, Reader.Controller.ConnectFailed, Reader.Controller.ConnectionLost,
		Reader.Controller.Reconnect, Reader.Controller.ReconnectFailed, Reader.Controller.ReconnectAborted, Reader.Controller.Error, Reader.Controller.Warning,
		Reader.Controller.Information
	};

	/**
	 * Defines all exit types with warning level.
	 */
	public static String[] Warning = new String[] {

		Common.Error, Common.Warning,

		Core.Cycle.EventCycle.Error,

		Core.Cycle.CommandCycle.Error,

		Core.Cycle.PortCycle.Error,

		Subscriber.Controller.Exited, Subscriber.Controller.Faulted, Subscriber.Controller.ReinitFailed,

		Subscriber.Container.DeliverFailed, Subscriber.Container.DeliverAborted, Subscriber.Container.ThresholdExceeded, Subscriber.Container.ThresholdEased,
		Subscriber.Container.ReportsLost, Subscriber.Container.InvokeFailed,

		Reader.Controller.Exited, Reader.Controller.Faulted, Reader.Controller.ReinitFailed, Reader.Controller.ReconnectFailed,
		Reader.Controller.ReconnectAborted, Reader.Controller.Error, Reader.Controller.Warning
	};

	/**
	 * Defines all exit types with error level.
	 */
	public static String[] Error = new String[] {

		Common.Error,

		Core.Cycle.EventCycle.Error,

		Core.Cycle.CommandCycle.Error,

		Core.Cycle.PortCycle.Error,

		Subscriber.Controller.ReinitFailed,

		Subscriber.Container.DeliverAborted, Subscriber.Container.ReportsLost, Subscriber.Container.InvokeFailed,

		Reader.Controller.ReinitFailed, Reader.Controller.ReconnectAborted, Reader.Controller.Error
	};
}