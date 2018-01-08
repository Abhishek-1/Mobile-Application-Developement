// IMyAidlInterface.aidl
package uic.sdmp.abhi.fedcash.common;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List monthlyCash(int year);
    List dalyCash(int day, int month, int year, int numberofworkingdays);
    String yearlyaverage(int year);
}
