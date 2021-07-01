export interface IApiary {
  id?: number;
  code?: string;
  name?: string;
  active?: boolean;
  description?: string;
  address?: string;
  ownerLogin?: string;
  ownerId?: number;
}

export class Apiary implements IApiary {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public active?: boolean,
    public description?: string,
    public address?: string,
    public ownerLogin?: string,
    public ownerId?: number
  ) {
    this.active = this.active || false;
  }
}
