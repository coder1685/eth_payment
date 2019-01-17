export class EthereumAddressModel {

  constructor(public accountName: string,
              public name: string,
              public ethereumAddress: string,
              public amount: number) {
  }

  public static newObject(): EthereumAddressModel {
    return new EthereumAddressModel( '', '', '' , 0);
  }


}
