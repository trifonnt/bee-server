import { Moment } from 'moment';

export interface IMeasurementRecord {
  id?: number;
  recordedAt?: Moment;
  inboundCount?: number;
  outboundCount?: number;
  deviceCode?: string;
  deviceId?: number;
}

export class MeasurementRecord implements IMeasurementRecord {
  constructor(
    public id?: number,
    public recordedAt?: Moment,
    public inboundCount?: number,
    public outboundCount?: number,
    public deviceCode?: string,
    public deviceId?: number
  ) {}
}
