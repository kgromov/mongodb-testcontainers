package org.kgromov.repository;

import org.kgromov.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

public interface DailyTemperatureRepository extends MongoRepository<DailyTemperature, String> {
    Optional<DailyTemperature> findByDate(LocalDate date);

    @Query("{'date': {$regex: ?0}}")
    List<DailyTemperature> findByDateInRange(String monthDay, Pageable pageable);

    @Aggregation(pipeline = {
            """
                {
                	$project: {
                		_id: 1,
                		morningTemperature: 1,
                		afternoonTemperature: 1,
                		eveningTemperature: 1,
                		nightTemperature: 1,
                		date: {
                			$dateToString: {
                				date: "$date",
                				format: "%Y-%m-%d"                				
                			}
                		}
                	}
                }
            """,
            """
                {
                	$match: {
                		date: {
                			$regex: ?0
                		}
                	}
                }/*,
                {
                	$sort: {
                		date: 1
                	}
                },
                {
                	$limit: ?1
                }*/
            """
    })
    List<DailyTemperature> findByDateInRangeAggregation(String monthDay, Pageable pageable);

    @Aggregation(pipeline = {
            """
                {
                	$group: {
                		_id: null,
                		min_date: {
                			$min: "$date"              			
                		},
                		max_date: {
                			$max: "$date"               			
                		},
                		
                	},
                	
                }
            """,
            """
                {
                	$project: {
                		minYear: {
                			$year: "$min_date"             			
                		},
                		maxYear: {
                			$year: "$max_date"               			
                		}
                	},
                	
                }
            """
    })
    YearsRange getYearsRange();

    @Aggregation(pipeline = {
            """
                        {
                           	$project: {
                           		_id: null,                                 
                           		year: {$year: "$date"},
                           		temp: {
                           			$avg: [
                           				"$morningTemperature",
                           				"$afternoonTemperature",
                           				"$nightTemperature"
                           			]
                           		},
                           		// season: {
                           		//   $switch:
                             //         {
                             //           branches: [
                             //             {
                             //               case: { $or : [ {$eq: [{$month: "$date"}, 1]}, {$eq : [{$month: "$date"}, 2]}, {$eq : ["$month", 12]} ] },
                             //               then: "Winter"
                             //             },
                             //             {
                             //               case: { $and : [ {$gt: [{$month: "$date"}, 2]}, {$lt : [{$month: "$date"}, 6]} ] },
                             //               then: "Spring"
                             //             },
                             //             {
                             //               case: { $and : [ {$gt: [{$month: "$date"}, 5]}, {$lt : [{$month: "$date"}, 9]} ] },
                             //               then: "Summer"
                             //             },
                             //             {
                             //               case: { $and : [ {$gt: [{$month: "$date"}, 8]}, {$lt : [{$month: "$date"}, 12]} ] },
                             //               then: "Autumn"
                             //             },
                             //           ],
                             //           default: "Not found"
                             //         }
                           		// }
                           	}
                           }
            """,
            """
                {
                     $group: {
                       _id: {year: "$year"},
                       temp: {$avg: "$temp"}
                     }
                }
            """,
            """
               {
                    $project: {
                      _id: 0,
                      year: "$_id.year",
                      temperature: "$temp"
                    }
               }
           """
    })
    List<YearAverageTemperature> getYearAverageTemperature(Sort sort);

    @Aggregation(pipeline = {
            """
                {
                   	$project: {
                   		_id: null,
                   		date: {
                   			$dateToString: {
                   				date: "$date",
                   				format: "%Y-%m-%d"
                   			}
                   		},
                   		temperature: {
                   			$min: [
                   				"$morningTemperature",
                   				"$afternoonTemperature",
                   				"$nightTemperature"
                   			]
                   		},
                   	}
                   }
            """,
            """
                {
                	$sort: {
                		temperature: 1
                	}       	
                }
            """,
            """
                {
                	$limit: 1   	
                }
            """
    })
    Optional<DayTemperature> getMinTemperature();

    @Aggregation(pipeline = {
            """
                {
                   	$project: {
                   		_id: null,
                   		date: {
                   			$dateToString: {
                   				date: "$date",
                   				format: "%Y-%m-%d"
                   			}
                   		},
                   		temperature: {
                   			$max: [
                   				"$morningTemperature",
                   				"$afternoonTemperature",
                   				"$nightTemperature"
                   			]
                   		},
                   	}
                   }
            """,
            """
                {
                	$sort: {
                		temperature: -1
                	}	
                }
            """,
            """
                {
                	$limit: 1
                }
            """
    })
    Optional<DayTemperature> getMaxTemperature();

   @Aggregation(pipeline = {
           """
             {
                $project: {
                  _id: null,
                  month:  {
                    $month: "$date",
                  },
                  year: {
                    $year: "$date",
                  },
                  minTemp: {
                    $min: [
                      "$morningTemperature",
                      "$afternoonTemperature",
                      "$nightTemperature",
                    ]
                  },
                  maxTemp: {
                    $max: [
                      "$morningTemperature",
                      "$afternoonTemperature",
                      "$nightTemperature",
                    ]
                  },
                  avgTemp: {
                    $avg: [
                      "$morningTemperature",
                      "$afternoonTemperature",
                      "$nightTemperature",
                    ]
                  }
                }
              }          
           """,
           """
           {
               $project: {
                 year: 1,
                 minTemp: 1,
                 maxTemp: 1,
                 avgTemp: 1,
                 season: {
                   $switch: {
                     branches: [
                       {
                         case: {
                           $or: [
                             {
                               $eq: ["$month", 1],
                             },
                             {
                               $eq: ["$month", 2],
                             },
                             {
                               $eq: ["$month", 12],
                             }
                           ]
                         },
                         then: "WINTER",
                       },
                       {
                         case: {
                           $and: [
                             {
                               $gt: ["$month", 2],
                             },
                             {
                               $lt: ["$month", 6],
                             }
                           ]
                         },
                         then: "SPRING",
                       },
                       {
                         case: {
                           $and: [
                             {
                               $gt: ["$month", 5],
                             },
                             {
                               $lt: ["$month", 9],
                             }
                           ]
                         },
                         then: "SUMMER",
                       },
                       {
                         case: {
                           $and: [
                             {
                               $gt: ["$month", 8],
                             },
                             {
                               $lt: ["$month", 12],
                             }
                           ]
                         },
                         then: "AUTUMN",
                       },
                     ],
                     default: "Not found",
                   },
                 },
               },
             }
           """,
           """
            {
                $group: {
                  _id: {
                    year: "$year",
                    season: "$season",
                  },
                  minTemp: {
                    $min: "$minTemp",
                  },
                  maxTemp: {
                    $max: "$maxTemp",
                  },
                  avgTemp: {
                    $avg: "$avgTemp",
                  }
                }
              }
           """,
           """
            {
               $project: {
                 _id: 0,
                 year: "$_id.year",
                 season: "$_id.season",
                 minTemp: "$minTemp",
                 maxTemp: "$maxTemp",
                 avgTemp: "$avgTemp",
               },
             }
           """
   })
    List<SeasonTemperature> getSeasonsTemperature(Pageable pageable);

    default List<YearBySeasonTemperature> getYearsBySeasonsTemperature(Pageable pageable) {
        return this.getSeasonsTemperature(pageable)
                .stream()
                .collect(groupingBy(SeasonTemperature::getYear))
                .entrySet()
                .stream()
                .map(groupByYear -> {
                    var seasons = groupByYear.getValue()
                            .stream()
                            .sorted(Comparator.comparing(s -> s.getSeason().ordinal()))
                            .toList();
                    return new YearBySeasonTemperature(groupByYear.getKey(), seasons);
                })
                .sorted(Comparator.comparing(YearBySeasonTemperature::getYear))
                .toList();
    }


}
