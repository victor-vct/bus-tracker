# Bus Tracker
Module from my final paper (TCC in portuguese) for tracking a bus, utilizing [Android Things](https://developer.android.com/things/index.html)

## Project

In my final paper, we will development app for visual impairment who needs take a bus. This repository contains the project for bus module that has functions:

- Tracker the bus
- Show notification to driver who needs to stop at next stop bus

## Hardware

- [Intel Edison](https://software.intel.com/en-us/iot/hardware/edison)
- [uBlox NEO 6M](https://www.u-blox.com/en/product/neo-6-series)

## Software

- [Android Things](https://developer.android.com/things/index.html)

## Architecture

I tried use Clean Architecture a little simplified. I think use more robust Clean Architecture, like in Android apps, in this project with Android Things, is a little over engineering. 

## Libraries

- Retrofit
- RxJava/RxAndroid
- Dagger [not implement yet]

## References

https://github.com/androidthings/contrib-drivers/tree/master/gps
