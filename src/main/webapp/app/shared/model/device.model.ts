export interface IDevice {
  id?: number;
  code?: string;
  name?: string;
  active?: boolean;
  description?: string;
}

export class Device implements IDevice {
  constructor(public id?: number, public code?: string, public name?: string, public active?: boolean, public description?: string) {
    this.active = this.active || false;
  }
}
