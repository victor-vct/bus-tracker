# Introduction

For people with visual impairment a simple task, such as taking the bus to go work, can become something very hard to do. For decades this sort of daily task wasn't thought to serve people with this sort of disability. With an increasing penetration of technologies such as smartphones and a better mobile network (i.e. 4G), there is no longer an acceptable excuse to keep these people excluded from a service of good quality. With this idea in mind, this final paper (TCC), proposes to build a system that identifies that a person with visual impariment has arrived on a bus stop, alerts the bus driver of this event, triggers the process of helping the user to enter the vehicle, improving, therefore, the final experience of the system's user.

## Goal

In this project, it will be developed an app for people with visual impairment who needs to take a bus. 
This repository contains the bus module that compose this system.

## Features

- Track the bus;
- Use push notifications to alert bus driver that there will be passengers with special needs on the next stop;

# Architecture

It was used a simplified version of Clean Architecture - use of the full specification, like in a regular Android application, in this project with Android Things would be over engineering the problem.

### Hardware

- [Intel Edison](https://software.intel.com/en-us/iot/hardware/edison)
- [uBlox NEO 6M](https://www.u-blox.com/en/product/neo-6-series)

### Software

- [Android Things](https://developer.android.com/things/index.html)

### Libraries

- Retrofit
- RxJava/RxAndroid
- Dagger

# References

- [GPS driver for Android Things](https://github.com/androidthings/contrib-drivers/tree/master/gps)

# Licence

```
MIT License

Copyright (c) 2017 Victor Vieira Paulino

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
```
