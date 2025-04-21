# Iranian Chronology

The **Humanist Iranian calendar** used in this project is the same as
the [*Solar Hijri calendar*](https://en.wikipedia.org/wiki/Solar_Hijri_calendar)
with a tiny difference in its year numbering system:
its year numbering starts with the foundation of the ancient city of [**Susa**](https://en.wikipedia.org/wiki/Susa),
marking the spark of civilisation in the region.

> Excavations have uncovered evidence of continual habitation dating back to 4395 BCE
> but that early community grew from an even older one dating back to c. 7000 BCE.
> ([World History Encyclopedia](https://www.worldhistory.org/timeline/susa/))

So the years until Christ's birth are 4395 and until the Hijrah (+621), it would be 5016.
If we ignore those 16 years, and add 5000 years into the normal Solar Hijri calendar,
we've got ourselves a new year numbering system whose conversion is quite easy!
You'll just have to turn the leading "1" in 1404 into "6"...

Here's an example of how it works:

| Solar Hijri | Humanist Iranian |
|:-----------:|:----------------:|
|    1404     |       6404       |
|    1400     |       6400       |
|    1378     |       6378       |

### Implementation in Java

The [**java.time**](https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/time/package-summary.html) package
(added since JDK 1.8) is the most modern way to handle date & time in JVM-based languages.

This calendar implementation has only 2 files:

- [**IranianChronology**](src/main/ir/mahdiparastesh/chrono/IranianChronology.java)
- [**IranianDate**](src/main/ir/mahdiparastesh/chrono/IranianDate.java)

#### Android

If you're developing an Android app, note that the *java.time* is supported since Android API 26+.
Although you can provide it for earlier Android APIs using the
[desugaring feature](https://developer.android.com/studio/write/java8-support-table).

I had also implemented the *Humanist Iranian* calendar for the
[*android.icu.util*](https://developer.android.com/reference/android/icu/util/package-summary) package which is based
upon the old famous [*ICU*](https://en.wikipedia.org/wiki/International_Components_for_Unicode) library.

- [HumanistIranianCalendar.kt](https://gist.github.com/fulcrum6378/68c82e1f03d12f5540af1359e2b690d9)
- [HumanistIranianCalendar.java](https://gist.github.com/fulcrum6378/62264825004f0ba83020c11db15567eb)

### Deployment

Simply implement the JAR output of this project as a dependency in your project.

### License

```
Copyright 2025 Mahdi Parastesh

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the “Software”),
to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom
the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
