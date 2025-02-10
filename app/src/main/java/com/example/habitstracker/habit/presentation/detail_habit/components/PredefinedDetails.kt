package com.example.habitstracker.habit.presentation.detail_habit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AirlineSeatReclineNormal
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.CoffeeMaker
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NaturePeople
import androidx.compose.material.icons.filled.NoFood
import androidx.compose.material.icons.filled.NoMeals
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Paragliding
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.PhoneDisabled
import androidx.compose.material.icons.filled.PhoneLocked
import androidx.compose.material.icons.filled.PhonelinkOff
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ScreenLockLandscape
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material.icons.filled.SocialDistance
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.SportsVolleyball
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.Color


val keepActiveGetFit = listOf(
    DefaultHabitDetailItem("Workout", "Move your body and enjoy burning fat", Icons.Default.FitnessCenter, Color(0xFFC26519)),
    DefaultHabitDetailItem("Walking", "Boost your steps daily", Icons.Default.DirectionsWalk, Color(0xFF4CAF50)),
    DefaultHabitDetailItem("Running", "Increase stamina and energy", Icons.Default.DirectionsRun, Color(0xFF8E24AA)),
    DefaultHabitDetailItem("Yoga", "Improve flexibility and calm your mind", Icons.Default.SelfImprovement, Color(0xFF009688)),
    DefaultHabitDetailItem("Swimming", "Full body workout without sweat", Icons.Default.Pool, Color(0xFF3A7BE7)),
    DefaultHabitDetailItem("Stretching", "Release tension and increase flexibility", Icons.Default.Accessibility, Color(0xFFFF6F00)),
    DefaultHabitDetailItem("Cycling", "Strengthen legs and explore outdoors", Icons.Default.PedalBike, Color(0xFF64B5F6)),
    DefaultHabitDetailItem("Jump rope", "Boost your cardio and coordination", Icons.Default.SportsVolleyball, Color(0xFFFF9800)),
    DefaultHabitDetailItem("Hiking", "Embrace nature and stay active", Icons.Default.Terrain, Color(0xFF4CAF50)),
    DefaultHabitDetailItem("Push-ups", "Increase upper body strength", Icons.Default.PushPin, Color(0xFF9C27B0)),
    DefaultHabitDetailItem("Plank challenge", "Strengthen your core", Icons.Default.Build, Color(0xFFFF5722)),
    DefaultHabitDetailItem("Block screen time", "Lock screen and glue attention", Icons.Default.PhonelinkOff, Color(0xFF0081CB)),
    DefaultHabitDetailItem("Sleep over 8h", "Enough time for sweet dreams", Icons.Default.Hotel, Color(0xFFFFA726))
)

val eatDrinkHealthily = listOf(
    DefaultHabitDetailItem(
        "Go sugar-free",
        "See your body change with being sugar-free",
        Icons.Default.NoMeals,
        iconColor = Color(0xFF64B5F6)
    ),
    DefaultHabitDetailItem(
        "Limit caffeine intake",
        "Replace with other healthy choices",
        Icons.Default.CoffeeMaker,
        iconColor = Color(0xFF81C784)
    ),
    DefaultHabitDetailItem(
        "No alcohol",
        "Life is way better without alcohol",
        Icons.Default.LocalBar,
        iconColor = Color(0xFFD32F2F)
    ),
    DefaultHabitDetailItem(
        "No fried food",
        "An easy way to lose fat",
        Icons.Default.Fastfood,
        iconColor = Color(0xFFFFA726)
    ),
    DefaultHabitDetailItem(
        "Intermittent fasting",
        "Your stomach needs a little rest",
        Icons.Default.Timer,
        iconColor = Color(0xFF607D8B)
    ),
    DefaultHabitDetailItem(
        "Take greens & fruits",
        "Don't forget the natural snacks",
        Icons.Default.Eco,
        iconColor = Color(0xFF4CAF50)
    ),
    DefaultHabitDetailItem(
        "Have breakfast",
        "Wake up your body with energy",
        Icons.Default.BreakfastDining,
        iconColor = Color(0xFF8E24AA)
    ),
    DefaultHabitDetailItem(
        "Drink 8 cups of water",
        "Keep your body hydrated",
        Icons.Default.LocalDrink,
        iconColor = Color(0xFF0288D1)
    ),
    DefaultHabitDetailItem(
        "Snack wisely",
        "Choose healthy snacks like nuts or fruits",
        Icons.Default.Spa,
        iconColor = Color(0xFF9CCC65)
    ),
    DefaultHabitDetailItem(
        "Plan your meals",
        "Avoid overeating by staying organized",
        Icons.Default.CalendarToday,
        iconColor = Color(0xFF3F51B5)
    ),
    DefaultHabitDetailItem(
        "Eat slowly",
        "Let your body recognize when it's full",
        Icons.Default.HourglassBottom,
        iconColor = Color(0xFF00ACC1)
    ),
    DefaultHabitDetailItem(
        "Prepare home-cooked meals",
        "Control ingredients and portions",
        Icons.Default.Kitchen,
        iconColor = Color(0xFFFF7043)
    ),
    DefaultHabitDetailItem("Avoid processed food", "Stick to whole and fresh ingredients", Icons.Default.Warning, iconColor = Color(0xFFF44336)),
    DefaultHabitDetailItem("Monitor your portion size", "Keep your servings balanced", Icons.Default.Scale, iconColor = Color(0xFF795548))
)

val easeStress = listOf(
    DefaultHabitDetailItem(
        "Practice meditation",
        "Connect with your inner peace",
        Icons.Default.SelfImprovement,
        iconColor = Color(0xFF64B5F6)
    ),
    DefaultHabitDetailItem(
        "Practice breathing",
        "Calm yourself from every in and out",
        Icons.Default.Air,
        iconColor = Color(0xFF81C784)
    ),
    DefaultHabitDetailItem(
        "Read a book",
        "Let your mind wander through stories",
        Icons.Default.MenuBook,
        iconColor = Color(0xFF5E35B1)
    ),
    DefaultHabitDetailItem(
        "Keep a journal",
        "Know yourself better through writing",
        Icons.Default.Create,
        iconColor = Color(0xFFE57373)
    ),
    DefaultHabitDetailItem(
        "Do stretching exercises",
        "Release tension and stress",
        Icons.Default.FitnessCenter,
        iconColor = Color(0xFF4CAF50)
    ),
    DefaultHabitDetailItem(
        "Do yoga",
        "Calming and smoothing",
        Icons.Default.SelfImprovement,
        iconColor = Color(0xFFEF6C00)
    ),
    DefaultHabitDetailItem(
        "Get enough sleep",
        "Allow your body to recover and recharge",
        Icons.Default.Bedtime,
        iconColor = Color(0xFF212121)
    ),
    DefaultHabitDetailItem(
        "Enjoy nature",
        "Embrace nature to calm your mind",
        Icons.Default.NaturePeople,
        iconColor = Color(0xFF66BB6A)
    ),
    DefaultHabitDetailItem(
        "Disconnect from devices",
        "Take a break from screens and notifications",
        Icons.Default.PhoneDisabled,
        iconColor = Color(0xFF9E9E9E)
    ),
    DefaultHabitDetailItem(
        "Laugh more",
        "Find a moment that makes you smile",
        Icons.Default.EmojiEmotions,
        iconColor = Color(0xFFFFEB3B)
    ),
    DefaultHabitDetailItem(
        "Allow yourself to cry",
        "Release emotional tension and feel relief",
        Icons.Default.Cloud,
        iconColor = Color(0xFF90CAF9)
    ),
    DefaultHabitDetailItem(
        "Declutter your space",
        "Organize your environment for mental clarity",
        Icons.Default.CleaningServices,
        iconColor = Color(0xFF795548)
    ),
    DefaultHabitDetailItem(
        "Practice gratitude",
        "Focus on positive aspects of your life",
        Icons.Default.Favorite,
        iconColor = Color(0xFFF44336)
    ),
    DefaultHabitDetailItem(
        "Take a warm bath",
        "Feel the healing power of relaxation",
        Icons.Default.Bathtub,
        iconColor = Color(0xFF03A9F4)
    ),
    DefaultHabitDetailItem(
        "Listen to calming music",
        "Let soothing melodies relax you",
        Icons.Default.MusicNote,
        iconColor = Color(0xFFAB47BC)
    )
)

val gainSelfDiscipline = listOf(
    DefaultHabitDetailItem(
        "Limit screen time",
        "Reduce distractions by controlling screen use",
        Icons.Default.ScreenLockLandscape,
        iconColor = Color(0xFF1E88E5)
    ),
    DefaultHabitDetailItem(
        "No emotional spending",
        "Distinguish your needs and wants",
        Icons.Default.AttachMoney,
        iconColor = Color(0xFF43A047)
    ),
    DefaultHabitDetailItem(
        "Go sugar-free",
        "See your body change with being sugar-free",
        Icons.Default.NoFood,
        iconColor = Color(0xFFD32F2F)
    ),
    DefaultHabitDetailItem(
        "Avoid alcohol",
        "Life is way better without alcohol",
        Icons.Default.LocalBar,
        iconColor = Color(0xFF8E24AA)
    ),
    DefaultHabitDetailItem(
        "Limit caffeine intake",
        "Replace with other healthy choices",
        Icons.Default.Coffee,
        iconColor = Color(0xFF795548)
    ),
    DefaultHabitDetailItem(
        "Quit smoking",
        "Don't have 'just one'",
        Icons.Default.SmokingRooms,
        iconColor = Color(0xFFB71C1C)
    ),
    DefaultHabitDetailItem(
        "Avoid fried food",
        "An easy way to lose fat",
        Icons.Default.Fastfood,
        iconColor = Color(0xFFF57C00)
    ),
    DefaultHabitDetailItem(
        "No excessive gaming",
        "Winners don't just exist in games",
        Icons.Default.VideogameAsset,
        iconColor = Color(0xFF43A047)
    ),
    DefaultHabitDetailItem(
        "Wake up early",
        "Start your day with a productive mindset",
        Icons.Default.WbSunny,
        iconColor = Color(0xFFFFEB3B)
    ),
    DefaultHabitDetailItem(
        "Create a to-do list",
        "Plan your day to avoid procrastination",
        Icons.Default.List,
        iconColor = Color(0xFF3949AB)
    ),
    DefaultHabitDetailItem(
        "Set small goals",
        "Achieve success step by step",
        Icons.Default.Lightbulb,
        iconColor = Color(0xFFFFA000)
    ),
    DefaultHabitDetailItem(
        "Keep a daily journal",
        "Track your progress and self-growth",
        Icons.Default.Edit,
        iconColor = Color(0xFF4CAF50)
    ),
    DefaultHabitDetailItem(
        "Clean up your workspace",
        "Less mess equals less stress",
        Icons.Default.CleaningServices,
        iconColor = Color(0xFF6D4C41)
    ),
    DefaultHabitDetailItem(
        "Practice delayed gratification",
        "Train yourself to resist instant pleasures",
        Icons.Default.HourglassEmpty,
        iconColor = Color(0xFF607D8B)
    ),
    DefaultHabitDetailItem(
        "Stay hydrated",
        "Drink water to maintain focus and energy",
        Icons.Default.LocalDrink,
        iconColor = Color(0xFF2196F3)
    )
)

val leisureMoments = listOf(
    DefaultHabitDetailItem(
        "Watch a movie",
        "Get yourself a film therapy",
        Icons.Default.Movie,
        iconColor = Color(0xFF0288D1)
    ),
    DefaultHabitDetailItem(
        "Practice a hobby",
        "Fill your time, life won't pass you by",
        Icons.Default.Brush,
        iconColor = Color(0xFF4CAF50)
    ),
    DefaultHabitDetailItem(
        "Learn a new language",
        "Deepen your connection to other cultures",
        Icons.Default.Language,
        iconColor = Color(0xFFD32F2F)
    ),
    DefaultHabitDetailItem(
        "Learn a musical instrument",
        "Run your fingers through your soul",
        Icons.Default.MusicNote,
        iconColor = Color(0xFFE91E63)
    ),
    DefaultHabitDetailItem(
        "Board games with friends",
        "More than just fun and games",
        Icons.Default.VideogameAsset,
        iconColor = Color(0xFF43A047)
    ),
    DefaultHabitDetailItem(
        "Draw or paint something",
        "Find your inner Van Gogh",
        Icons.Default.Palette,
        iconColor = Color(0xFFF57C00)
    ),
    DefaultHabitDetailItem(
        "Take a photo",
        "Record it as lifetime memories",
        Icons.Default.PhotoCamera,
        iconColor = Color(0xFF673AB7)
    ),
    DefaultHabitDetailItem(
        "Time for self-care",
        "Take time to nourish your body properly",
        Icons.Default.Spa,
        iconColor = Color(0xFF009688)
    ),
    DefaultHabitDetailItem(
        "Read a book",
        "Lose yourself in another world",
        Icons.Default.Book,
        iconColor = Color(0xFF1E88E5)
    ),
    DefaultHabitDetailItem(
        "Go for a nature walk",
        "Reconnect with nature and yourself",
        Icons.Default.Forest,
        iconColor = Color(0xFF66BB6A)
    ),
    DefaultHabitDetailItem(
        "Attend a live event",
        "Enjoy live music, theater, or sports",
        Icons.Default.Event,
        iconColor = Color(0xFFC2185B)
    ),
    DefaultHabitDetailItem(
        "Journal your thoughts",
        "Reflect on your experiences and emotions",
        Icons.Default.Edit,
        iconColor = Color(0xFFFFC107)
    ),
    DefaultHabitDetailItem(
        "Try a new recipe",
        "Experiment with flavors and cuisines",
        Icons.Default.Restaurant,
        iconColor = Color(0xFF9C27B0)
    ),
    DefaultHabitDetailItem(
        "Stargaze",
        "Lose track of time under the night sky",
        Icons.Default.Star,
        iconColor = Color(0xFFFF9800)
    ),
    DefaultHabitDetailItem(
        "Call a friend",
        "Strengthen your connections",
        Icons.Default.Call,
        iconColor = Color(0xFF607D8B)
    )
)

val goodMorningHabits = listOf(
    DefaultHabitDetailItem(
        "Get up early",
        "Shine before the sun does",
        Icons.Default.WbSunny,
        iconColor = Color(0xFFDEEA13)
    ),
    DefaultHabitDetailItem(
        "Dress up",
        "A good look lights up your day",
        Icons.Default.Checkroom,
        iconColor = Color(0xFF795548)
    ),
    DefaultHabitDetailItem(
        "Have breakfast",
        "Wake up your body with energy",
        Icons.Default.Restaurant,
        iconColor = Color(0xFFFF9800)
    ),
    DefaultHabitDetailItem(
        "Make the bed",
        "Clean your bed, clear your mind",
        Icons.Default.Bed,
        iconColor = Color(0xFF9C27B0)
    ),
    DefaultHabitDetailItem(
        "Empty trash",
        "Leave home with no mess",
        Icons.Default.Delete,
        iconColor = Color(0xFFE91E63)
    ),
    DefaultHabitDetailItem(
        "Say 'You're the best!'",
        "Start your day with positive affirmations",
        Icons.Default.EmojiEmotions,
        iconColor = Color(0xFFFFEB3B)
    ),
    DefaultHabitDetailItem(
        "Warm up",
        "A natural morning pick-me-up",
        Icons.Default.FitnessCenter,
        iconColor = Color(0xFF43A047)
    ),
    DefaultHabitDetailItem(
        "Plan your day",
        "Set your intentions and priorities",
        Icons.Default.CalendarToday,
        iconColor = Color(0xFF607D8B)
    ),
    DefaultHabitDetailItem(
        "Meditate",
        "Connect with your inner peace",
        Icons.Default.SelfImprovement,
        iconColor = Color(0xFF009688)
    ),
    DefaultHabitDetailItem(
        "Hydrate",
        "Start your day with a glass of water",
        Icons.Default.LocalDrink,
        iconColor = Color(0xFF1E88E5)
    ),
    DefaultHabitDetailItem(
        "Listen to music",
        "Boost your mood with your favorite tunes",
        Icons.Default.MusicNote,
        iconColor = Color(0xFFD32F2F)
    ),
    DefaultHabitDetailItem(
        "Stretch",
        "Release tension and wake up your muscles",
        Icons.Default.AirlineSeatReclineNormal,
        iconColor = Color(0xFF4CAF50)
    ),
    DefaultHabitDetailItem(
        "Smile at yourself in the mirror",
        "Acknowledge yourself with kindness",
        Icons.Default.Face,
        iconColor = Color(0xFFFF5722)
    )
)

val beforeSleepRoutineHabits = listOf(
    DefaultHabitDetailItem(
        "Stop eating before bed",
        "Your stomach also needs a good sleep",
        Icons.Default.NoFood,
        iconColor = Color(0xFFB71C1C)
    ),
    DefaultHabitDetailItem(
        "Go to bed early",
        "Early to bed, early to rise",
        Icons.Default.Bedtime,
        iconColor = Color(0xFF0D47A1)
    ),
    DefaultHabitDetailItem(
        "Stretch",
        "Release tension & stress",
        Icons.Default.FitnessCenter,
        iconColor = Color(0xFF43A047)
    ),
    DefaultHabitDetailItem(
        "Practice meditation",
        "Connect with your inner peace",
        Icons.Default.SelfImprovement,
        iconColor = Color(0xFF00ACC1)
    ),
    DefaultHabitDetailItem(
        "Practice breathing",
        "Calm yourself from every in and out",
        Icons.Default.Air,
        iconColor = Color(0xFF1DE9B6)
    ),
    DefaultHabitDetailItem(
        "Keep a journal",
        "Reflect on your day and release thoughts",
        Icons.Default.EditNote,
        iconColor = Color(0xFFFF6F00)
    ),
    DefaultHabitDetailItem(
        "Read a book",
        "Never enough books before bedtime",
        Icons.Default.MenuBook,
        iconColor = Color(0xFF5E35B1)
    ),
    DefaultHabitDetailItem(
        "Take a bath",
        "Feel the healing power of a warm bath",
        Icons.Default.Bathtub,
        iconColor = Color(0xFF0288D1)
    ),
    DefaultHabitDetailItem(
        "Drink hot milk",
        "Relax your mind and body with warm milk",
        Icons.Default.LocalCafe,
        iconColor = Color(0xFFF57F17)
    ),
    DefaultHabitDetailItem(
        "Set tomorrow's plan",
        "Organize your tasks for a smooth day ahead",
        Icons.Default.EventNote,
        iconColor = Color(0xFF455A64)
    ),
    DefaultHabitDetailItem(
        "Dim the lights",
        "Create a calming atmosphere",
        Icons.Default.LightMode,
        iconColor = Color(0xFFFFB300)
    ),
    DefaultHabitDetailItem(
        "Turn off screens",
        "Disconnect and let your mind unwind",
        Icons.Default.DoNotDisturb,
        iconColor = Color(0xFFD50000)
    ),
    DefaultHabitDetailItem(
        "Express gratitude",
        "End your day with positive reflections",
        Icons.Default.ThumbUp,
        iconColor = Color(0xFF388E3C)
    ),
    DefaultHabitDetailItem(
        "Visualize dreams",
        "Picture your ideal future before sleeping",
        Icons.Default.Paragliding,
        iconColor = Color(0xFF7E57C2)
    )
)

val masterProductivityHabits = listOf(
    DefaultHabitDetailItem(
        "Set small goals",
        "Make goals more specific and achievable",
        Icons.Default.TipsAndUpdates,
        iconColor = Color(0xFF0288D1)
    ),
    DefaultHabitDetailItem(
        "Work with white noise",
        "A scientific way to overcome distraction",
        Icons.Default.Audiotrack,
        iconColor = Color(0xFF29B6F6)
    ),
    DefaultHabitDetailItem(
        "Prepare tomorrow's to-do list",
        "Make your time visible and countable",
        Icons.Default.List,
        iconColor = Color(0xFF558B2F)
    ),
    DefaultHabitDetailItem(
        "Get things done",
        "Never put off until tomorrow",
        Icons.Default.Task,
        iconColor = Color(0xFFE53935)
    ),
    DefaultHabitDetailItem(
        "Clean up workspace",
        "Less mess equals less stress",
        Icons.Default.CleaningServices,
        iconColor = Color(0xFF689F38)
    ),
    DefaultHabitDetailItem(
        "Block screen time",
        "Lock screen and glue attention",
        Icons.Default.PhoneLocked,
        iconColor = Color(0xFFFF6D00)
    ),
    DefaultHabitDetailItem(
        "Get up early",
        "Shine before the sun does",
        Icons.Default.WbSunny,
        iconColor = Color(0xFFFFA000)
    ),
    DefaultHabitDetailItem(
        "Check all unread",
        "Zero your inbox",
        Icons.Default.Mail,
        iconColor = Color(0xFF1E88E5)
    ),
    DefaultHabitDetailItem(
        "Focus on one task",
        "Avoid multitasking to increase efficiency",
        Icons.Default.CenterFocusStrong,
        iconColor = Color(0xFF7B1FA2)
    ),
    DefaultHabitDetailItem(
        "Track your progress",
        "Measure your achievements daily",
        Icons.Default.TrendingUp,
        iconColor = Color(0xFF4CAF50)
    ),
    DefaultHabitDetailItem(
        "Take regular breaks",
        "Stay productive without burnout",
        Icons.Default.AirlineSeatReclineNormal,
        iconColor = Color(0xFF00695C)
    ),
    DefaultHabitDetailItem(
        "Set priorities",
        "Handle the most important tasks first",
        Icons.Default.PriorityHigh,
        iconColor = Color(0xFFD81B60)
    ),
    DefaultHabitDetailItem(
        "Review your goals",
        "Adjust your plans for continuous improvement",
        Icons.Default.AssignmentTurnedIn,
        iconColor = Color(0xFF009688)
    )
)

val strongerMindHabits = listOf(
    DefaultHabitDetailItem(
        "Read every day",
        "Expand your knowledge and creativity",
        Icons.Default.MenuBook,
        iconColor = Color(0xFF4CAF50)
    ),
    DefaultHabitDetailItem(
        "Meditate regularly",
        "Improve focus and reduce anxiety",
        Icons.Default.SelfImprovement,
        iconColor = Color(0xFF00ACC1)
    ),
    DefaultHabitDetailItem(
        "Learn something new",
        "Expand your horizons daily",
        Icons.Default.School,
        iconColor = Color(0xFFFFA726)
    ),
    DefaultHabitDetailItem(
        "Keep a journal",
        "Reflect on your thoughts and experiences",
        Icons.Default.Edit,
        iconColor = Color(0xFF7E57C2)
    ),
    DefaultHabitDetailItem(
        "Practice gratitude",
        "Appreciate the good things in your life",
        Icons.Default.ThumbUp,
        iconColor = Color(0xFFFF5722)
    ),
    DefaultHabitDetailItem(
        "Solve puzzles",
        "Keep your brain sharp and active",
        Icons.Default.Extension,
        iconColor = Color(0xFF5C6BC0)
    ),
    DefaultHabitDetailItem(
        "Avoid negative thoughts",
        "Train your mind to focus on positivity",
        Icons.Default.RemoveCircle,
        iconColor = Color(0xFFD32F2F)
    ),
    DefaultHabitDetailItem(
        "Practice deep breathing",
        "Calm your mind and body",
        Icons.Default.Air,
        iconColor = Color(0xFF64B5F6)
    ),
    DefaultHabitDetailItem(
        "Develop critical thinking",
        "Question and analyze everything critically",
        Icons.Default.Analytics,
        iconColor = Color(0xFF009688)
    ),
    DefaultHabitDetailItem(
        "Limit social media",
        "Protect your mind from digital overload",
        Icons.Default.SocialDistance,
        iconColor = Color(0xFF607D8B)
    ),
    DefaultHabitDetailItem(
        "Listen to podcasts",
        "Gain new perspectives and insights",
        Icons.Default.Podcasts,
        iconColor = Color(0xFF8E24AA)
    ),
    DefaultHabitDetailItem(
        "Take mindful breaks",
        "Recharge by stepping away from work",
        Icons.Default.HourglassEmpty,
        iconColor = Color(0xFFBDBDBD)
    ),
    DefaultHabitDetailItem(
        "Learn a new language",
        "Enhance your cognitive abilities",
        Icons.Default.Language,
        iconColor = Color(0xFF673AB7)
    ),
    DefaultHabitDetailItem(
        "Maintain a balanced diet",
        "Fuel your brain with healthy nutrients",
        Icons.Default.NoMeals,
        iconColor = Color(0xFF9E9E9E)
    ),
    DefaultHabitDetailItem(
        "Develop problem-solving skills",
        "Challenge yourself to find solutions",
        Icons.Default.Lightbulb,
        iconColor = Color(0xFFFFC107)
    )
)