# Fitness Manager

### Proposal

Many times I've found myself wanting to workout, but feeling lost because I don't know what I should do. What muscle 
group should I work on? What exercises should I build the workout with? Not only is it exhausting to have to come up
with these plans every single time you want to workout, but it can also subconsciously lead you to not want to workout
at all. And this is coming from someone with a background in competitive sport –– trust me, I know the struggle. <br>

I'm designing this fitness manager application to make working out easier, so that when you work out, you can focus on
the important stuff and not stress out over the planning process. This way, you can simply select the muscle group you
want to focus on, select a workout, and go! It will keeps all your exercises and workouts organized for you. Anyone can
use it; you don't need to be a gym shark or an athlete. The goal is to encourage everyone to live a healthy lifestyle,
regardless of their fitness background! <br>

This app will allow you to build a workout from the ground up.
It will allow you to:
- Create a new workout
- Edit a workout
- Categorize a workout (muscle group)
- Store a workout <br> <br>

### User Stories <br>

- As a user, I want to be able to create a new workout and add it to a list of workouts in a category (muscle group)
- As a user, I want to be able to select a category and view all the workouts in that category
- As a user, I want to be able to add a workout to a category
- As a user, I want to be able to remove a workout from a category

- As a user, I want to be able to select a workout and view all the exercises in that workout
- As a user, I want to be able to add an exercise to my workout
- As a user, I want to be able to remove an exercise from my workout

- As a user, I want to be able to edit the exercise (eg. change the amount of time it's done for)

- As a user, when I quit the application, I want my workouts to be automatically saved to file
- As a user, when I start the application, I want my workouts to be automatically loaded from file

#### Phase 4: Task 2 <br>

I have chosen to test and design a robust class for this task. The viewCategory() method in the Category class throws a 
checked exception (EmptyCategoryException) when its list of workouts is empty. I have also made the viewWorkout() 
method in the Workout class implement similar behaviour (using EmptyWorkoutException) when its list of exercises is 
empty.

#### Phase 4: Task 3 <br>

- Reduce coupling by creating an abstract superclass for FitnessManager and FitnessManagerApp that implements all the 
  functionality for JsonReader, JsonWriter, and Container
- Increase cohesion by splitting up FitnessManagerApp into three classes, one dealing with the main app, one dealing 
  with the respective lists for Categories, Workouts, and Exercises, and a third class for all the buttons

