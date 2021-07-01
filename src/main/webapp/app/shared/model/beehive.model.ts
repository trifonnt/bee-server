export interface IBeehive {
  id?: number;
  code?: string;
  name?: string;
  active?: boolean;
  description?: string;
  yearCreated?: number;
  monthCreated?: number;
  apiaryCode?: string;
  apiaryId?: number;
}

export class Beehive implements IBeehive {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public active?: boolean,
    public description?: string,
    public yearCreated?: number,
    public monthCreated?: number,
    public apiaryCode?: string,
    public apiaryId?: number
  ) {
    this.active = this.active || false;
  }
}
